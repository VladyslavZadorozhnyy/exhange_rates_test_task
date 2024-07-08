package com.test.exchangeratetesttask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.exchangeratetesttask.data.db.dao.BalanceDao
import com.test.exchangeratetesttask.data.db.entity.BalanceEntity

@Database(
    entities = [BalanceEntity::class],
    exportSchema = false,
    version = 5,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun balanceDao(): BalanceDao
}