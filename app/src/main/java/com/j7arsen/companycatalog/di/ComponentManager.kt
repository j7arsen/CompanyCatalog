package com.j7arsen.companycatalog.di

import android.content.Context
import com.j7arsen.companycatalog.di.app.AppComponent
import com.j7arsen.companycatalog.di.app.DaggerAppComponent
import com.j7arsen.companycatalog.di.app.company.CompanyComponent
import com.j7arsen.companycatalog.di.app.module.AppModule

object ComponentManager {

    private var appComponent : AppComponent? = null
    private var companyComponent : CompanyComponent? = null

    fun initAppComponent(context: Context){
        appComponent = DaggerAppComponent.builder().appModule(AppModule(context)).context(context).build()
    }

    fun getCompanyComponent() : CompanyComponent {
        return companyComponent ?: appComponent!!.companyComponentBuilder().build()
    }

    fun destroyCompanyComponent() {
        companyComponent = null
    }

}