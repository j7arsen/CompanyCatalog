package com.j7arsen.companycatalog.di.app.module

import com.j7arsen.companycatalog.di.app.AppScope
import com.j7arsen.companycatalog.di.app.qualifier.Global
import com.j7arsen.companycatalog.di.app.qualifier.Local
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone

@Module
class NavigationModule {

    private val localCicerone = Cicerone.create()
    private val globalCicerone = Cicerone.create()

    @AppScope
    @Provides
    @Local
    fun localRouter() = localCicerone.router

    @AppScope
    @Provides
    @Local
    fun localNavigatorHolder() = localCicerone.navigatorHolder

    @AppScope
    @Provides
    @Global
    fun globalRouter() = globalCicerone.router

    @AppScope
    @Provides
    @Global
    fun globalNavigatorHolder() = globalCicerone.navigatorHolder

}