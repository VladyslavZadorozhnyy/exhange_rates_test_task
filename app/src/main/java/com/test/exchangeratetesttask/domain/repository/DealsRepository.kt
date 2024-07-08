package com.test.exchangeratetesttask.domain.repository

interface DealsRepository {
    fun getDealsCount(): Int

    fun incrementDealsCount(): Int
}