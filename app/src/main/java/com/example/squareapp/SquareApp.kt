package com.example.squareapp

import android.app.Application
import com.example.squareapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SquareApp : Application() {
  override fun onCreate() {
    super.onCreate()

    //Instantiate Koin
    startKoin {
      androidContext(this@SquareApp)
      modules(appModule)
    }
  }
}