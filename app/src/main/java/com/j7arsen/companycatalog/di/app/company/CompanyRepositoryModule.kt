package com.j7arsen.companycatalog.di.app.company

import com.j7arsen.data.api.ICompanyService
import com.j7arsen.data.model.mapper.Mapper
import com.j7arsen.data.model.net.CompanyDetailEntity
import com.j7arsen.data.model.net.CompanyEntity
import com.j7arsen.data.model.repository.CompanyRepository
import com.j7arsen.data.model.repository.mapper.CompanyDetailMapper
import com.j7arsen.data.model.repository.mapper.CompanyMapper
import com.j7arsen.domain.model.CompanyDetailModel
import com.j7arsen.domain.model.CompanyModel
import com.j7arsen.domain.repository.ICompanyRepository
import dagger.Module
import dagger.Provides

@Module
class CompanyRepositoryModule {

    @CompanyScope
    @Provides
    fun companyMapper() : Mapper<CompanyEntity, CompanyModel> = CompanyMapper()

    @CompanyScope
    @Provides
    fun companyDetailMapper() : Mapper<CompanyDetailEntity, CompanyDetailModel> = CompanyDetailMapper()

    @CompanyScope
    @Provides
    fun companyRepository(companyService: ICompanyService, companyMapper : Mapper<CompanyEntity, CompanyModel>, companyDetailMapper : Mapper<CompanyDetailEntity, CompanyDetailModel>) : ICompanyRepository = CompanyRepository(companyService, companyMapper, companyDetailMapper)

}