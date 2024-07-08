package com.test.exchangeratetesttask.data.db

import android.content.Context
import androidx.room.Room
import com.test.exchangeratetesttask.common.Constants
import com.test.exchangeratetesttask.data.db.dao.AppDaoDatabase
import com.test.exchangeratetesttask.data.db.dao.BalanceDao

class AppDaoDatabaseImpl(
    private val context: Context,
) : AppDaoDatabase {
    private var instance: AppDatabase? = null

    private fun getInstance(): AppDatabase {
        synchronized(AppDatabase::class.java) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDatabase::class.java,
                    name = Constants.DATABASE_NAME,
                ).fallbackToDestructiveMigration().build()
            }
        }
        return instance!!
    }

    override fun getBalanceDao(): BalanceDao {
        return getInstance().balanceDao()
    }
}