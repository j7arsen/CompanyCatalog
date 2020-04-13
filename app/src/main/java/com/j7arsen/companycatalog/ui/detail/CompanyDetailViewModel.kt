package com.j7arsen.companycatalog.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.j7arsen.companycatalog.base.BaseViewModel
import com.j7arsen.companycatalog.di.ComponentManager
import com.j7arsen.companycatalog.dispathers.IODispatcher
import com.j7arsen.companycatalog.utils.error.ErrorData
import com.j7arsen.companycatalog.utils.error.ErrorHandler
import com.j7arsen.domain.interactor.GetCompanyDetailUseCase
import com.j7arsen.domain.model.CompanyDetailModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class CompanyDetailViewModel(private val companyId: String?) : BaseViewModel(), CoroutineScope {

    @Inject
    lateinit var errorHandlerFactory: ErrorHandler

    @Inject
    @Named("io")
    lateinit var dispatcherFactory: IODispatcher

    @Inject
    lateinit var getCompanyDetailUseCase: GetCompanyDetailUseCase

    private val _screenState: MutableLiveData<DetailCompanyViewModelState> =
        MutableLiveData()
    val screenState: LiveData<DetailCompanyViewModelState> = _screenState

    private val _companyDetail: MutableLiveData<CompanyDetailModel> = MutableLiveData()
    val companyDetail: LiveData<CompanyDetailModel> = _companyDetail

    private val job: Job = SupervisorJob()
    private val handler = CoroutineExceptionHandler { _, exception ->
        _screenState.value = DetailCompanyViewModelState.ErrorLoading(
            errorHandlerFactory.getError(
                exception
            )
        )
    }

    override val coroutineContext: CoroutineContext
        get() = handler + job + Dispatchers.Main

    init {
        ComponentManager.getCompanyComponent().inject(this)
        loadCompanyDetail()
    }

    @ExperimentalCoroutinesApi
    fun loadCompanyDetail() {
        launch {
            getCompanyDetailUseCase.execute(dispatcherFactory, companyId)
                .onStart {
                    _screenState.value = DetailCompanyViewModelState.ShowLoading
                }
                .collect {
                    _screenState.value = DetailCompanyViewModelState.CompleteLoading
                    _companyDetail.value = it
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (coroutineContext.isActive) {
            coroutineContext.cancel()
        }
    }

    sealed class DetailCompanyViewModelState {

        object ShowLoading : DetailCompanyViewModelState()

        object CompleteLoading : DetailCompanyViewModelState()

        data class ErrorLoading(val errorData: ErrorData) : DetailCompanyViewModelState()

    }

}