package com.test.exchangeratetesttask.presentation.states

import com.test.exchangeratetesttask.domain.model.CurrencyModel

data class BalancesState(
    val balances: List<CurrencyModel>? = null,
    val message: String = "",
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = true,
    val updateSpinners: Boolean = false,
)