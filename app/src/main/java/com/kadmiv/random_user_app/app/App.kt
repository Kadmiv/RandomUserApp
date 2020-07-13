package com.kadmiv.random_user_app.app

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.kadmiv.random_user_app.di.appModule
import com.kadmiv.random_user_app.di.presenterModule
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(applicationContext)

        // получить список всех модулей
        val moduleList = listOf(presenterModule, appModule)

        // запустить koin со списком модулей
        startKoin(this, moduleList)
    }
}