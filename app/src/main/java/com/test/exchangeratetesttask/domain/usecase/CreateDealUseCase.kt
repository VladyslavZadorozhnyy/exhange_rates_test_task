package com.test.exchangeratetesttask.domain.usecase

import android.util.Log
import com.test.exchangeratetesttask.R
import com.test.exchangeratetesttask.common.Constants
import com.test.exchangeratetesttask.common.Resource
import com.test.exchangeratetesttask.domain.model.CurrencyModel
import com.test.exchangeratetesttask.domain.repository.BalancesRepository
import com.test.exchangeratetesttask.domain.repository.DealsRepository
import com.test.exchangeratetesttask.domain.repository.StringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CreateDealUseCase(
    private val balancesRepository: BalancesRepository,
    private val dealsCountRepository: DealsRepository,
    private val stringRepository: StringRepository,
) {
    operator fun invoke(
        rcvCurr: CurrencyModel,
        sellCurr: CurrencyModel,
        rcvAmount: Float,
        sellAmount: Float,
    ): Flow<Resource<String>> = flow {
        var result: Resource<String> = Resource.Loading()
        emit(result)

        withContext(Dispatchers.IO) {
            val applyComm = dealsCountRepository.getDealsCount() > Constants.FREE_DEALS
            val commission = sellAmount * Constants.COMMISSION_PER_CENTS / 100F
            val totalSellAmount = if (applyComm) sellAmount + commission else sellAmount
            val successMessage = if (applyComm) {
                stringRepository.getStringFormatted(R.string.success_commission_applied, sellAmount, sellCurr.currencyCode, rcvAmount, rcvCurr.currencyCode, commission, sellCurr.currencyCode)
            } else {
                stringRepository.getStringFormatted(R.string.success_commission_not_applied, sellAmount, sellCurr.currencyCode, rcvAmount, rcvCurr.currencyCode)
            }

            val rcvBalance = rcvCurr.balance + rcvAmount
            val sellBalance = sellCurr.balance - totalSellAmount
            result = if (totalSellAmount > sellCurr.balance) {
                Resource.Failure(message = stringRepository.getString(R.string.failure_exceed_balance))
            } else {
                balancesRepository.updateItem(sellCurr.currencyCode, sellBalance)
                balancesRepository.updateItem(rcvCurr.currencyCode, rcvBalance)

                dealsCountRepository.incrementDealsCount()
                Resource.Success(message = successMessage, data = null)
            }
        }
        emit(result)
    }
}