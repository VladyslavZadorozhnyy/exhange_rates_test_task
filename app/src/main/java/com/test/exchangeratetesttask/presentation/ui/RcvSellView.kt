package com.test.exchangeratetesttask.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.test.exchangeratetesttask.R
import com.test.exchangeratetesttask.common.Constants
import com.test.exchangeratetesttask.databinding.RcvSellLayoutBinding
import android.R.layout.simple_spinner_dropdown_item as spinner_item

class RcvSellView(
    private val context: Context,
    attrs: AttributeSet? = null,
): ConstraintLayout(context, attrs) {
    private lateinit var controller: InputController

    private val binding by lazy {
        RcvSellLayoutBinding.inflate(LayoutInflater.from(context), this, true) }

    fun setupSellView(controller: InputController) {
        this.controller = controller
        setup(R.drawable.sell_icon, R.string.sell, R.string.sell_prompt)
    }

    fun setupRcvView(controller: InputController) {
        this.controller = controller
        setup(R.drawable.receive_icon, R.string.receive, R.string.rcv_prompt)
    }

    fun setupSpinner(currencies: List<String>) {
        binding.spinner.apply {
            adapter = ArrayAdapter(context, spinner_item, currencies)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    controller.calculateRate() }
            }
        }
    }

    fun getCurrency(): String { return binding.spinner.selectedItem?.toString() ?: "" }

    fun setAmount(value: Float) {
        if (!binding.input.hasFocus()) binding.input.setText(Constants.FORMAT_PATTERN.format(value))
    }

    fun getAmount(): Float { return binding.input.text.toString().toFloatOrNull() ?: 0F }

    private fun setup(iconRes: Int, textRes: Int, promptRes: Int) {
        setupInput()
        binding.apply {
            rcvSellIcon.setImageResource(iconRes)
            rcvSellText.text = context.getString(textRes)
            spinner.prompt = context.getString(promptRes)
        }
    }

    private fun setupInput() {
        binding.input.doOnTextChanged { _, _, _, _ ->
            if (binding.input.hasFocus()) controller.calculateRate() }
    }

    companion object {
        enum class Type { RECEIVING, SELLING }
    }

    interface InputController {
        fun calculateRate()
    }
}