package com.test.exchangeratetesttask.presentation.viewmodels.business_logic

import com.test.exchangeratetesttask.domain.model.CurrencyModel
import com.test.exchangeratetesttask.presentation.ui.RcvSellView.Companion.Type

class SellAmountDefined(
    rcvCur: CurrencyModel?,
    sellCur: CurrencyModel?,
    rcvAmount: Float?,
    private val sellAmount: Float?,
    private val dealType: Type,
): DealValidityStrategy(rcvCur, sellCur, rcvAmount, sellAmount, dealType) {
    override fun valid(): Boolean {
        return if (dealType == Type.SELLING) sellAmount != 0F else true
    }
}