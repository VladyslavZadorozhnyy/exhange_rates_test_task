package com.test.exchangeratetesttask

import android.app.Application
import com.test.exchangeratetesttask.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ExchangeRateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ExchangeRateApp)
            modules(appModule)
        }
    }
}