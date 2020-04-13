package com.j7arsen.companycatalog.app

import android.app.Application
import com.j7arsen.companycatalog.di.ComponentManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentManager.initAppComponent(this)
    }

}