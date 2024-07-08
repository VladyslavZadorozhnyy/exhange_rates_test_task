package com.test.exchangeratetesttask.common

import android.R
import android.app.AlertDialog
import android.content.Context


object Utils {
    fun showDlg(context: Context, titleId: Int, message: String) {
        AlertDialog.Builder(context)
            .setTitle(titleId)
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }
}