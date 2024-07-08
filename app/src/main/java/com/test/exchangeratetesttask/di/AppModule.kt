package com.test.exchangeratetesttask.di

import com.test.exchangeratetesttask.data.db.AppDaoDatabaseImpl
import com.test.exchangeratetesttask.data.db.dao.AppDaoDatabase
import com.test.exchangeratetesttask.data.repository.BalancesRepositoryImpl
import com.test.exchangeratetesttask.data.repository.DealsCountRepositoryImpl
import com.test.exchangeratetesttask.data.repository.ExchangeRatesRepositoryImpl
import com.test.exchangeratetesttask.data.repository.StringRepositoryImpl
import com.test.exchangeratetesttask.data.web.api.ExchangeApiService
import com.test.exchangeratetesttask.domain.repository.BalancesRepository
import com.test.exchangeratetesttask.domain.repository.DealsRepository
import com.test.exchangeratetesttask.domain.repository.ExchangeRatesRepository
import com.test.exchangeratetesttask.domain.repository.StringRepository
import com.test.exchangeratetesttask.domain.usecase.CreateDealUseCase
import com.test.exchangeratetesttask.domain.usecase.GetCurrenciesUseCase
import com.test.exchangeratetesttask.presentation.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
//  ViewModels
    viewModel { MainViewModel(get(), get()) }

//  UseCases
    single { GetCurrenciesUseCase(get(), get(), get()) }
    single { CreateDealUseCase(get(), get(), get()) }

//  Repositories
    single<ExchangeRatesRepository> { ExchangeRatesRepositoryImpl(get()) }
    single<DealsRepository> { DealsCountRepositoryImpl(androidApplication()) }
    single<BalancesRepository> { BalancesRepositoryImpl(get()) }
    single<StringRepository> { StringRepositoryImpl(get()) }

//    Other
    single { ExchangeApiService() }
    single<AppDaoDatabase> { AppDaoDatabaseImpl(androidApplication()) }
}