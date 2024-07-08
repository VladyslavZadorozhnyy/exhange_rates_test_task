package com.test.exchangeratetesttask.presentation.viewmodels.business_logic

import com.test.exchangeratetesttask.domain.model.CurrencyModel
import com.test.exchangeratetesttask.presentation.ui.RcvSellView.Companion.Type

class RcvAmountDefined(
    rcvCur: CurrencyModel?,
    sellCur: CurrencyModel?,
    private val rcvAmount: Float?,
    sellAmount: Float?,
    private val dealType: Type,
): DealValidityStrategy(rcvCur, sellCur, rcvAmount, sellAmount, dealType) {
    override fun valid(): Boolean {
        return if (dealType == Type.RECEIVING) rcvAmount != 0F else true
    }
}