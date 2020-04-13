package com.j7arsen.domain.interactor

import com.j7arsen.domain.interactor.base.BaseListUseCase
import com.j7arsen.domain.model.CompanyModel
import com.j7arsen.domain.repository.ICompanyRepository
import kotlinx.coroutines.flow.Flow

class GetCompanyListUseCase(private val companyRepository: ICompanyRepository) : BaseListUseCase<CompanyModel, Unit>() {

    override fun buildUseCaseObservable(params: Unit?): Flow<List<CompanyModel>> = companyRepository.getCompanyList()

}