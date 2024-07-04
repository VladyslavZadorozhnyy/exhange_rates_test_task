package com.test.exchangeratetesttask.data.web.response

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime
import java.io.Serializable

data class ExchangeRatesResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: LocalDateTime,
    @SerializedName("rates")
    val rates: List<Pair<String, Float>>,
) : Serializable