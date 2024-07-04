package com.test.exchangeratetesttask.domain.model.web

import kotlinx.datetime.LocalDateTime

data class ExchangeRatesModel(
    val base: String,
    val date: LocalDateTime,
    val rates: List<Pair<String, Float>>,
)