package com.test.exchangeratetesttask.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.exchangeratetesttask.common.Constants

@Entity(tableName = Constants.BALANCES_TABLE_NAME)
data class BalanceEntity(
    @ColumnInfo(name = "currencyCode") val currencyCode: String,
    @ColumnInfo(name = "balance") val balance: Float,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)