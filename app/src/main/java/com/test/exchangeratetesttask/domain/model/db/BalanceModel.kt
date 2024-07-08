package com.test.exchangeratetesttask.domain.model.db

import com.test.exchangeratetesttask.data.db.entity.BalanceEntity

data class BalanceModel(
    val currencyCode: String,
    val balance: Float,
) { companion object }

fun BalanceModel.Companion.fromEntity(entity: BalanceEntity): BalanceModel {
    return BalanceModel(entity.currencyCode, entity.balance)
}