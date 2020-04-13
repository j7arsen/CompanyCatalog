package com.j7arsen.data.api

import com.j7arsen.data.model.net.CompanyDetailEntity
import com.j7arsen.data.model.net.CompanyEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ICompanyService {

    @GET(GET_COMPANY)
    suspend fun getCompanyList(): List<CompanyEntity>

    @GET(GET_COMPANY)
    suspend fun getCompanyDetail(@Query(RequestField.ID) id: String): List<CompanyDetailEntity>

}