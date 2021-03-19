package com.homecredit.exam


import android.app.Application
import com.homecredit.exam.di.module.appModule
import com.homecredit.exam.di.module.repoModule
import com.homecredit.exam.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}
