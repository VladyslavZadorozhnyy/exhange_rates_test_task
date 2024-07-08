package com.test.exchangeratetesttask.domain.repository

import com.test.exchangeratetesttask.domain.model.web.ExchangeRatesModel

interface ExchangeRatesRepository {
    fun fetchExchangeRates(): ExchangeRatesModel?
}