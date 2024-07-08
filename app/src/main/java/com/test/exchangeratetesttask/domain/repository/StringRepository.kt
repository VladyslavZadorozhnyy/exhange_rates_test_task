package com.test.exchangeratetesttask.domain.repository

interface StringRepository {
    fun getString(stringId: Int): String

    fun getStringFormatted(stringId: Int, vararg formatArgs: Any): String
}