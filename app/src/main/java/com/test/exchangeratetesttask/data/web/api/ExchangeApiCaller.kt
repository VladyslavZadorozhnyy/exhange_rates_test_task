package com.test.exchangeratetesttask.data.web.api

import com.test.exchangeratetesttask.data.web.response.ExchangeRatesResponse
import retrofit2.Call
import retrofit2.http.GET

interface ExchangeApiCaller {
    @GET("tasks/api/currency-exchange-rates")
    fun getExchangeRates(): Call<ExchangeRatesResponse>
}