package com.j7arsen.companycatalog.di.app.module

import android.content.Context
import com.j7arsen.companycatalog.di.app.AppScope
import com.j7arsen.companycatalog.di.app.ApplicationContext
import com.j7arsen.companycatalog.utils.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @AppScope
    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context) = ResourceProvider(context)

}