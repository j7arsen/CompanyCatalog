package com.j7arsen.data.model.repository

import com.j7arsen.data.api.ICompanyService
import com.j7arsen.data.model.mapper.Mapper
import com.j7arsen.data.model.net.CompanyDetailEntity
import com.j7arsen.data.model.net.CompanyEntity
import com.j7arsen.domain.model.CompanyDetailModel
import com.j7arsen.domain.model.CompanyModel
import com.j7arsen.domain.repository.ICompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CompanyRepository(
    private val companyService: ICompanyService,
    private val mapperCompanyList: Mapper<CompanyEntity, CompanyModel>,
    private val mapperCompanyDetail: Mapper<CompanyDetailEntity, CompanyDetailModel>
) : ICompanyRepository {

    override fun getCompanyList(): Flow<List<CompanyModel>> =
        flow { emit(companyService.getCompanyList()) }.map { mapperCompanyList.map(it) }

    override fun getCompanyDetail(id: String): Flow<CompanyDetailModel> =
        flow { emit(companyService.getCompanyDetail(id)[0]) }.map { mapperCompanyDetail.map(it) }
}