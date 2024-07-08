package com.test.exchangeratetesttask.data.repository

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.test.exchangeratetesttask.common.Constants
import com.test.exchangeratetesttask.domain.repository.DealsRepository


class DealsCountRepositoryImpl(
    application: Application,
) : DealsRepository {
    private val masterKey = MasterKey.Builder(application, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        application,
        Constants.SHARED_PREF_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun getDealsCount(): Int {
        return sharedPreferences.getInt(Constants.DEALS_FIELD_NAME, 0)
    }

    override fun incrementDealsCount(): Int {
        sharedPreferences.edit()
            .putInt(Constants.DEALS_FIELD_NAME, getDealsCount() + 1)
            .apply()

        return sharedPreferences.getInt(Constants.DEALS_FIELD_NAME, 0)
    }
}