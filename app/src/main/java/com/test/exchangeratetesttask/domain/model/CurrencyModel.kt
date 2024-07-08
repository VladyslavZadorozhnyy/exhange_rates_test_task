package com.test.exchangeratetesttask.domain.model

import com.test.exchangeratetesttask.common.Constants

data class CurrencyModel(
    val currencyCode: String,
    val rate: Float,
    var balance: Float = 0F,
) {
    fun toLabel(): String {
        return Constants.FORMAT_PATTERN.format(balance) + " $currencyCode"
    }
}