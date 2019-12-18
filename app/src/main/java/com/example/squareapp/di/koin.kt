package com.example.squareapp.di

import com.example.squareapp.main.MainViewModel
import com.example.squareapp.repo.Repository
import com.example.squareapp.repo.SquareRepository
import com.example.squareapp.service.EmployeeService
import com.example.squareapp.util.baseUrl
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
  single {
    Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(MoshiConverterFactory.create())
      .build().create(EmployeeService::class.java)
  }

  single { Picasso.get().apply { setIndicatorsEnabled(true) } }


  single<Repository> {
    SquareRepository(get())
  }

  viewModel { MainViewModel(get(), Dispatchers.IO) }
}