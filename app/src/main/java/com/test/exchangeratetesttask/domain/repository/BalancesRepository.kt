package com.test.exchangeratetesttask.domain.repository

import com.test.exchangeratetesttask.data.db.entity.BalanceEntity
import com.test.exchangeratetesttask.domain.model.db.BalanceModel

interface BalancesRepository {
    fun insert(entity: BalanceEntity)

    fun updateItem(currencyCode: String, balance: Float)

    fun getAll(): List<BalanceEntity>

    fun getByCurrencyCode(currCode: String): BalanceModel?

    fun deleteByCurrencyCode(currCode: String)
}