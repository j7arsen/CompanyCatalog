package com.j7arsen.companycatalog.di.app.company

import com.j7arsen.companycatalog.ui.company.CompanyActivity
import com.j7arsen.companycatalog.ui.detail.CompanyDetailFragment
import com.j7arsen.companycatalog.ui.detail.CompanyDetailViewModel
import com.j7arsen.companycatalog.ui.list.CompanyListFragment
import com.j7arsen.companycatalog.ui.list.CompanyListViewModel
import dagger.Subcomponent

@CompanyScope
@Subcomponent(modules = [CompanyRepositoryModule::class, CompanyUseCaseModule::class])
interface CompanyComponent {

    fun inject(companyActivity: CompanyActivity)

    fun inject(componentListFragment: CompanyListFragment)

    fun inject(componentListViewModel: CompanyListViewModel)

    fun inject(componentDetailCompanyFragment: CompanyDetailFragment)

    fun inject(componentDetailCompanyViewModel: CompanyDetailViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): CompanyComponent
    }

}