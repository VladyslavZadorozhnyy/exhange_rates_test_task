package com.test.exchangeratetesttask.data.repository

import com.test.exchangeratetesttask.data.db.dao.AppDaoDatabase
import com.test.exchangeratetesttask.data.db.entity.BalanceEntity
import com.test.exchangeratetesttask.domain.model.db.BalanceModel
import com.test.exchangeratetesttask.domain.model.db.fromEntity
import com.test.exchangeratetesttask.domain.repository.BalancesRepository

class BalancesRepositoryImpl(
    private val database: AppDaoDatabase,
) : BalancesRepository {
    override fun insert(entity: BalanceEntity) {
        database.getBalanceDao().insert(entity)
    }

    override fun updateItem(currencyCode: String, balance: Float) {
        database.getBalanceDao().updateItem(currencyCode, balance)
    }

    override fun getAll(): List<BalanceEntity> {
        return database.getBalanceDao().getAll()
    }

    override fun getByCurrencyCode(currCode: String): BalanceModel? {
        val result: BalanceEntity? = database.getBalanceDao().getByCurrencyCode(currCode)
        return if (result != null) BalanceModel.fromEntity(result) else null
    }

    override fun deleteByCurrencyCode(currCode: String) {
        database.getBalanceDao().deleteByCurrencyCode(currCode)
    }
}