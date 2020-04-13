package com.j7arsen.domain.interactor

import com.j7arsen.domain.interactor.base.BaseUseCase
import com.j7arsen.domain.model.CompanyDetailModel
import com.j7arsen.domain.repository.ICompanyRepository
import kotlinx.coroutines.flow.Flow

class GetCompanyDetailUseCase(private val companyRepository: ICompanyRepository) :
    BaseUseCase<CompanyDetailModel, String>() {

    override fun buildUseCaseObservable(params: String?): Flow<CompanyDetailModel> =
        companyRepository.getCompanyDetail(params!!)
}