package com.test.exchangeratetesttask.data.db.dao

import androidx.room.*
import com.test.exchangeratetesttask.common.Constants
import com.test.exchangeratetesttask.data.db.entity.BalanceEntity

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: BalanceEntity)

    @Query("UPDATE ${Constants.BALANCES_TABLE_NAME} SET balance=:bal WHERE currencyCode=:currCode")
    fun updateItem(currCode: String, bal: Float)

    @Query("SELECT * FROM ${Constants.BALANCES_TABLE_NAME}")
    fun getAll(): List<BalanceEntity>

    @Query("SELECT * FROM ${Constants.BALANCES_TABLE_NAME} WHERE currencyCode=:currCode")
    fun getByCurrencyCode(currCode: String): BalanceEntity?

    @Query("DELETE FROM ${Constants.BALANCES_TABLE_NAME} WHERE currencyCode=:currCode")
    fun deleteByCurrencyCode(currCode: String)
}