package com.test.exchangeratetesttask.data.web.api

import com.test.exchangeratetesttask.data.web.response.ExchangeRatesResponse
import retrofit.Call
import retrofit.http.GET

interface ExchangeApiCaller {
    @GET("tasks/api/currency-exchange-rates")
    fun getExchangeRates(): Call<ExchangeRatesResponse>
}