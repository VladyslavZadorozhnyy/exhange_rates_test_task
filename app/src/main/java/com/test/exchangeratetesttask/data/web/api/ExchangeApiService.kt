package com.test.exchangeratetesttask.data.web.api

import com.test.exchangeratetesttask.common.Constants
import com.test.exchangeratetesttask.data.web.response.ExchangeRatesResponse
import retrofit.*

class ExchangeApiService {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val caller by lazy { retrofit.create(ExchangeApiCaller::class.java) }

    fun getExchangeRates(
        onSuccess: (ExchangeRatesResponse) -> Unit,
        onFailure: () -> Unit,
    ) {
        val call = caller.getExchangeRates()
        call.enqueue(object : Callback<ExchangeRatesResponse> {
            override fun onResponse(
                response: Response<ExchangeRatesResponse>?,
                retrofit: Retrofit?
            ) {
                if (response?.body() is ExchangeRatesResponse) onSuccess.invoke(response.body())
                else onFailure.invoke()
            }

            override fun onFailure(t: Throwable?) {
                onFailure.invoke()
            }
        })
    }
}