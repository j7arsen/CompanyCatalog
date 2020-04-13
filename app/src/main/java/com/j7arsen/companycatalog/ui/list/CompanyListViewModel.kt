package com.j7arsen.companycatalog.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.j7arsen.companycatalog.base.BaseViewModel
import com.j7arsen.companycatalog.di.ComponentManager
import com.j7arsen.companycatalog.dispathers.IODispatcher
import com.j7arsen.companycatalog.utils.error.ErrorData
import com.j7arsen.companycatalog.utils.error.ErrorHandler
import com.j7arsen.domain.interactor.GetCompanyListUseCase
import com.j7arsen.domain.model.CompanyModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class CompanyListViewModel : BaseViewModel(), CoroutineScope {

    @Inject
    lateinit var errorHandlerFactory: ErrorHandler

    @Inject
    @Named("io")
    lateinit var dispatcherFactory: IODispatcher

    @Inject
    lateinit var getCompanyListUseCase: GetCompanyListUseCase

    private val _screenState: MutableLiveData<CompanyListViewModelState> = MutableLiveData()
    val screenState: LiveData<CompanyListViewModelState> = _screenState

    private val _companyModelList: MutableLiveData<List<CompanyModel>> = MutableLiveData()
    val companyModelList: LiveData<List<CompanyModel>> = _companyModelList

    private val job: Job = SupervisorJob()
    private val handler = CoroutineExceptionHandler { _, exception ->
        _screenState.value =
            CompanyListViewModelState.ErrorLoading(errorHandlerFactory.getError(exception))
    }

    override val coroutineContext: CoroutineContext
        get() = handler + job + Dispatchers.Main

    init {
        ComponentManager.getCompanyComponent().inject(this)
        loadCompanyList()
    }

    @ExperimentalCoroutinesApi
    fun loadCompanyList() {
        launch {
            getCompanyListUseCase.execute(dispatcherFactory)
                .onStart {
                    _screenState.value = CompanyListViewModelState.HideEmptyList
                    _screenState.value = CompanyListViewModelState.ShowLoading
                }
                .collect {
                    _screenState.value = CompanyListViewModelState.CompleteLoading
                    if (it.isNotEmpty()) {
                        _companyModelList.value = it
                    } else {
                        _screenState.value = CompanyListViewModelState.ShowEmptyList
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (coroutineContext.isActive) {
            coroutineContext.cancel()
        }
    }

    sealed class CompanyListViewModelState {

        object ShowLoading : CompanyListViewModelState()

        object CompleteLoading : CompanyListViewModelState()

        data class ErrorLoading(val errorData: ErrorData) : CompanyListViewModelState()

        object ShowEmptyList : CompanyListViewModelState()

        object HideEmptyList : CompanyListViewModelState()

    }

}