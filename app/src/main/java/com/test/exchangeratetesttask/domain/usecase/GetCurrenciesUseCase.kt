package com.test.exchangeratetesttask.domain.usecase

import com.test.exchangeratetesttask.R
import com.test.exchangeratetesttask.common.Constants
import com.test.exchangeratetesttask.common.Resource
import com.test.exchangeratetesttask.data.db.entity.BalanceEntity
import com.test.exchangeratetesttask.domain.model.CurrencyModel
import com.test.exchangeratetesttask.domain.repository.BalancesRepository
import com.test.exchangeratetesttask.domain.repository.ExchangeRatesRepository
import com.test.exchangeratetesttask.domain.repository.StringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetCurrenciesUseCase(
    private val balancesRepository: BalancesRepository,
    private val ratesRepository: ExchangeRatesRepository,
    private val stringRepository: StringRepository,
) {
    operator fun invoke(): Flow<Resource<List<CurrencyModel>>> = flow {
        var result: Resource<List<CurrencyModel>> = Resource.Loading()
        emit(result)

        result = Resource.Failure()
        withContext(Dispatchers.IO) {
            result = try {
                val balances = balancesRepository.getAll()
                val currencies = ratesRepository.fetchExchangeRates()
                    ?: throw java.lang.Exception(stringRepository.getString(R.string.web_error_message))

                currencies.rates.forEach { currRate ->
                    var currBalance = balances.find { balance -> currRate.currencyCode == balance.currencyCode }

                    if (currBalance == null) {
                        val currencyBalance = if (currRate.currencyCode == currencies.base)
                            Constants.DEFAULT_BALANCE else Constants.ZERO_BALANCE

                        currBalance = BalanceEntity(currRate.currencyCode, currencyBalance)
                        balancesRepository.insert(currBalance)
                    }
                    currRate.balance = currBalance.balance
                }
                Resource.Success(currencies.rates)
            } catch (t: Throwable) {
                Resource.Failure(t.message ?: "Unknown exception")
            }
        }
        emit(result)
    }
}