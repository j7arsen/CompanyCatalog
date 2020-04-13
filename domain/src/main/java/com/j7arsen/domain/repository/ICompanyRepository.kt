package com.j7arsen.domain.repository

import com.j7arsen.domain.model.CompanyDetailModel
import com.j7arsen.domain.model.CompanyModel
import kotlinx.coroutines.flow.Flow

interface ICompanyRepository {

    fun getCompanyList(): Flow<List<CompanyModel>>

    fun getCompanyDetail(id: String): Flow<CompanyDetailModel>

}