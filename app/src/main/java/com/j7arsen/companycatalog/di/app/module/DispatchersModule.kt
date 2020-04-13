package com.j7arsen.companycatalog.di.app.module

import com.j7arsen.companycatalog.di.app.AppScope
import com.j7arsen.companycatalog.dispathers.DefaultDispatcher
import com.j7arsen.companycatalog.dispathers.IODispatcher
import com.j7arsen.companycatalog.dispathers.MainDispatcher
import com.j7arsen.companycatalog.dispathers.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DispatchersModule {

    @AppScope
    @Provides
    @Named("main")
    fun mainDispatcher() = MainDispatcher()

    @AppScope
    @Provides
    @Named("io")
    fun ioDispatcher() = IODispatcher()

    @AppScope
    @Provides
    @Named("default")
    fun defaultDispatcher() = DefaultDispatcher()

    @AppScope
    @Provides
    @Named("unconfined")
    fun unconfinedDispatcher() = UnconfinedDispatcher()

}