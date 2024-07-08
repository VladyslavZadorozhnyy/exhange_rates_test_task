package com.test.exchangeratetesttask.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.exchangeratetesttask.domain.usecase.CreateDealUseCase
import com.test.exchangeratetesttask.domain.usecase.GetCurrenciesUseCase
import com.test.exchangeratetesttask.presentation.states.BalancesState
import com.test.exchangeratetesttask.presentation.states.CalculationState
import com.test.exchangeratetesttask.presentation.ui.RcvSellView.Companion.Type
import com.test.exchangeratetesttask.presentation.viewmodels.business_logic.AmountsDefined
import com.test.exchangeratetesttask.presentation.viewmodels.business_logic.CurrenciesNonSame
import com.test.exchangeratetesttask.presentation.viewmodels.business_logic.RcvAmountDefined
import com.test.exchangeratetesttask.presentation.viewmodels.business_logic.SellAmountDefined
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val createDealUseCase: CreateDealUseCase,
) : ViewModel() {
    private val _balancesState = MutableLiveData(BalancesState())
    val balancesState: LiveData<BalancesState> = _balancesState

    private val _calculationState = MutableLiveData(CalculationState())
    val calculationState: LiveData<CalculationState> = _calculationState

    fun calculateRate(
        rcvCurCode: String,
        sellCurCode: String,
        rcvAmount: Float,
        sellAmount: Float,
        dealType: Type,
    ) {
        val rcvCurr = balancesState.value?.balances?.find { it.currencyCode == rcvCurCode }!!
        val sellCurr = balancesState.value?.balances?.find { it.currencyCode == sellCurCode }!!

        val currenciesNonSame = CurrenciesNonSame(
            rcvCurr, sellCurr, rcvAmount, sellAmount, dealType).valid()

        val validityStrategies = listOf(
            AmountsDefined(rcvCurr, sellCurr, rcvAmount, sellAmount, dealType),
            RcvAmountDefined(rcvCurr, sellCurr, rcvAmount, sellAmount, dealType),
            SellAmountDefined(rcvCurr, sellCurr, rcvAmount, sellAmount, dealType)
        )

        if (validityStrategies.any { !it.valid() }) {
            _calculationState.value = CalculationState(
                isSuccessful = false,
            )
            return
        }

        val calculatedRcvAmount = if (dealType == Type.RECEIVING) rcvAmount
        else sellAmount * (rcvCurr.rate / sellCurr.rate)

        val calculatedSellAmount = if (dealType == Type.SELLING) sellAmount
        else rcvAmount * (sellCurr.rate / rcvCurr.rate)

        _calculationState.value = CalculationState(
            isSuccessful = currenciesNonSame,
            receive = calculatedRcvAmount,
            sell = calculatedSellAmount,
        )
    }

    fun createDeal(
        rcvCurrCode: String,
        sellCurrCode: String,
        rcvAmount: Float,
        sellAmount: Float,
    ) {
        createDealUseCase(
            balancesState.value?.balances?.find { it.currencyCode == rcvCurrCode }!!,
            balancesState.value?.balances?.find { it.currencyCode == sellCurrCode }!!,
            rcvAmount,
            sellAmount,
        ).onEach {
            _calculationState.value = CalculationState(
                sellAmount,
                rcvAmount,
                sellCurrCode,
                rcvCurrCode,
                it.message,
                it.isLoading,
                it.isSuccessful
            )
        }.launchIn(viewModelScope)
    }

    fun getCurrencies() {
        val currsBefore = balancesState.value?.balances?.map { it.currencyCode } ?: listOf()
        getCurrenciesUseCase().onEach {
            val currsNow = it.data?.map { itt -> itt.currencyCode }

            _balancesState.value = BalancesState(it.data, it.message, it.isLoading, it.isSuccessful,
                updateSpinners = currsNow != currsBefore)
        }.launchIn(viewModelScope)
    }
}