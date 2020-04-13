package com.j7arsen.companycatalog.di.app

import android.content.Context
import com.j7arsen.companycatalog.di.app.company.CompanyComponent
import com.j7arsen.companycatalog.di.app.module.*
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, ApiModule::class, DispatchersModule::class, UtilsModule::class, NavigationModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun appModule(module: AppModule): Builder

        fun build(): AppComponent
    }

    fun companyComponentBuilder(): CompanyComponent.Builder

}