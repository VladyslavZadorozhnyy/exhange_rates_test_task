package com.test.exchangeratetesttask.data.web.response

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


data class ExchangeRatesResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: JsonObject,
)