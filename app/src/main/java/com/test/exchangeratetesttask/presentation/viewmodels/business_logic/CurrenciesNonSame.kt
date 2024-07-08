package com.test.exchangeratetesttask.presentation.viewmodels.business_logic

import com.test.exchangeratetesttask.domain.model.CurrencyModel
import com.test.exchangeratetesttask.presentation.ui.RcvSellView.Companion.Type

class CurrenciesNonSame(
    private val rcvCur: CurrencyModel?,
    private val sellCur: CurrencyModel?,
    rcvAmount: Float?,
    sellAmount: Float?,
    dealType: Type,
): DealValidityStrategy(rcvCur, sellCur, rcvAmount, sellAmount, dealType) {
    override fun valid(): Boolean {
        return rcvCur?.currencyCode != null
                && sellCur?.currencyCode != null && rcvCur.currencyCode != sellCur.currencyCode
    }
}