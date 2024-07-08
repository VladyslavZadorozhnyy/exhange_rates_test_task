package com.test.exchangeratetesttask.domain.model.web

import com.test.exchangeratetesttask.data.web.response.ExchangeRatesResponse
import com.test.exchangeratetesttask.domain.model.CurrencyModel
import kotlinx.datetime.LocalDate

data class ExchangeRatesModel(
    val base: String = "",
    val date: LocalDate? = null,
    val rates: List<CurrencyModel> = arrayListOf(),
) { companion object }

fun ExchangeRatesModel.Companion.fromResponse(response: ExchangeRatesResponse): ExchangeRatesModel {
    val currencyRatesList = arrayListOf<CurrencyModel>()
    response.rates.entrySet().forEach { key ->
        val currencyRate = key.value.toString().toFloat()
        currencyRatesList.add(CurrencyModel(key.key, currencyRate))
    }

    return ExchangeRatesModel(
        response.base,
        LocalDate.parse(response.date),
        currencyRatesList,
    )
}