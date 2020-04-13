package com.j7arsen.companycatalog.di.app.module

import android.content.Context
import com.j7arsen.companycatalog.di.app.AppScope
import com.j7arsen.companycatalog.di.app.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @AppScope
    @Provides
    @ApplicationContext
    fun context() = context


}