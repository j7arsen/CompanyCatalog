package com.j7arsen.companycatalog.di.app.company

import com.j7arsen.domain.interactor.GetCompanyDetailUseCase
import com.j7arsen.domain.interactor.GetCompanyListUseCase
import com.j7arsen.domain.repository.ICompanyRepository
import dagger.Module
import dagger.Provides

@Module
class CompanyUseCaseModule {

    @CompanyScope
    @Provides
    fun companyListUseCase(iCompanyRepository: ICompanyRepository) : GetCompanyListUseCase =
        GetCompanyListUseCase(iCompanyRepository)

    @CompanyScope
    @Provides
    fun companyDetailUseCase(iCompanyRepository: ICompanyRepository) : GetCompanyDetailUseCase =
        GetCompanyDetailUseCase(iCompanyRepository)

}