package com.test.exchangeratetesttask.data.repository

import android.util.Log
import com.test.exchangeratetesttask.data.web.api.ExchangeApiService
import com.test.exchangeratetesttask.domain.model.web.ExchangeRatesModel
import com.test.exchangeratetesttask.domain.model.web.fromResponse
import com.test.exchangeratetesttask.domain.repository.ExchangeRatesRepository

class ExchangeRatesRepositoryImpl(
    private val api: ExchangeApiService,
) : ExchangeRatesRepository {

    override fun fetchExchangeRates(): ExchangeRatesModel? {
        val resp = api.getExchangeRates()

        return if (resp.isSuccessful) ExchangeRatesModel.fromResponse(resp.body()) else null
    }

}