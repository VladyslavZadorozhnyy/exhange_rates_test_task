package com.test.exchangeratetesttask.presentation.viewmodels.business_logic

import com.test.exchangeratetesttask.domain.model.CurrencyModel
import com.test.exchangeratetesttask.presentation.ui.RcvSellView.Companion.Type

class AmountsDefined(
    rcvCur: CurrencyModel?,
    sellCur: CurrencyModel?,
    private val rcvAmount: Float?,
    private val sellAmount: Float?,
    dealType: Type,
) : DealValidityStrategy(rcvCur, sellCur, rcvAmount, sellAmount, dealType) {
    override fun valid(): Boolean {
        return !(rcvAmount == 0F && sellAmount == 0F)
    }
}