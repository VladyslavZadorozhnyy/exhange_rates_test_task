package com.test.exchangeratetesttask.data.web.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.exchangeratetesttask.common.Constants
import com.test.exchangeratetesttask.data.web.response.ExchangeRatesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ExchangeApiService {
    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    private val caller by lazy { retrofit.create(ExchangeApiCaller::class.java) }

    fun getExchangeRates(): Response<ExchangeRatesResponse> {
        return caller.getExchangeRates().execute()
    }
}