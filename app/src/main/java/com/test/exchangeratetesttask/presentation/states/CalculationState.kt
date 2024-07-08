package com.test.exchangeratetesttask.presentation.states

data class CalculationState(
    val sell: Float = 0F,
    val receive: Float = 0F,
    val sellingCurCode: String = "",
    val rcvCurr: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
)