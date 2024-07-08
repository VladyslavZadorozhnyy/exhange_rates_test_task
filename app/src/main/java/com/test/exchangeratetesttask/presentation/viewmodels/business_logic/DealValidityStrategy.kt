package com.test.exchangeratetesttask.presentation.viewmodels.business_logic

import com.test.exchangeratetesttask.domain.model.CurrencyModel
import com.test.exchangeratetesttask.presentation.ui.RcvSellView.Companion.Type

abstract class DealValidityStrategy(
    rcvCur: CurrencyModel?,
    sellCur: CurrencyModel?,
    rcvAmount: Float?,
    sellAmount: Float?,
    dealType: Type,
) {
    abstract fun valid(): Boolean
}