package com.test.exchangeratetesttask.presentation.ui

import android.os.Bundle
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.test.exchangeratetesttask.R
import com.test.exchangeratetesttask.common.*
import com.test.exchangeratetesttask.databinding.ActivityMainBinding
import com.test.exchangeratetesttask.domain.model.CurrencyModel
import com.test.exchangeratetesttask.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.test.exchangeratetesttask.presentation.ui.RcvSellView.Companion.Type


class MainActivity : AppCompatActivity(), RcvSellView.InputController {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainActivityVm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.sellLayout.setupSellView(this)
        binding.rcvLayout.setupRcvView(this)
        setupStaticViews()
        setupViewModels()
        mainActivityVm.getCurrencies()
    }

    override fun calculateRate() {
        if (binding.rcvLayout.hasFocus() && binding.sellLayout.hasFocus()) return

        mainActivityVm.calculateRate(
            rcvCurCode = binding.rcvLayout.getCurrency(),
            sellCurCode = binding.sellLayout.getCurrency(),
            rcvAmount = binding.rcvLayout.getAmount(),
            sellAmount = binding.sellLayout.getAmount(),
            dealType = if (binding.rcvLayout.hasFocus()) Type.RECEIVING else Type.SELLING,
        )
    }

    private fun setupViewModels() {
        mainActivityVm.balancesState.observe(this) { bState ->
            bState.balances?.let { setupDynamicViews(it, bState.updateSpinners) }
            updateLoading(bState.isLoading)

            if (!bState.isSuccessful) Utils.showDlg(this, R.string.failure, bState.message)
        }
        mainActivityVm.calculationState.observe(this) { cState ->
            binding.submitButton.isEnabled = cState.isSuccessful
            val statusTitle = if (cState.isSuccessful) R.string.success else R.string.failure

            if (cState.message.isNotEmpty()) {
                if (cState.isSuccessful) mainActivityVm.getCurrencies()
                Utils.showDlg(this, statusTitle, cState.message)
            } else {
                binding.rcvLayout.setAmount(cState.receive)
                binding.sellLayout.setAmount(cState.sell)
            }
        }
    }

    private fun setupDynamicViews(currencies: List<CurrencyModel>, updateSpinners: Boolean) {
        binding.currenciesList.text = currencies
            .sortedByDescending { it.balance }
            .joinToString(Constants.SEPARATOR) { it.toLabel() }

        if (!updateSpinners) return
        binding.sellLayout.setupSpinner(currencies.map { it.currencyCode })
        binding.rcvLayout.setupSpinner(currencies.map { it.currencyCode })
    }

    private fun setupStaticViews() {
        binding.submitButton.setOnClickListener { performExchange() }
        window.statusBarColor = ContextCompat.getColor(this, R.color.cyan)
        setContentView(binding.root)
    }

    private fun performExchange() {
        binding.rcvLayout.clearFocus()
        binding.sellLayout.clearFocus()

        mainActivityVm.createDeal(
            rcvCurrCode = binding.rcvLayout.getCurrency(),
            sellCurrCode = binding.sellLayout.getCurrency(),
            rcvAmount = binding.rcvLayout.getAmount(),
            sellAmount = binding.sellLayout.getAmount(),
        )
    }

    private fun updateLoading(isLoading: Boolean) {
        binding.loadingLayout.visibility = if (isLoading) VISIBLE else GONE
    }
}