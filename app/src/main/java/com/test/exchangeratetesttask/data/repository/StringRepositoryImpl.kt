package com.test.exchangeratetesttask.data.repository

import android.content.Context
import com.test.exchangeratetesttask.domain.repository.StringRepository

class StringRepositoryImpl(
    private val context: Context,
) : StringRepository {
    override fun getString(stringId: Int): String {
        return context.getString(stringId)
    }

    override fun getStringFormatted(stringId: Int, vararg formatArgs: Any): String {
        return context.getString(stringId, *formatArgs)
    }
}