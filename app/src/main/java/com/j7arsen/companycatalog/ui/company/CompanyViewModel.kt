package com.j7arsen.companycatalog.ui.company

import androidx.lifecycle.LiveData
import com.j7arsen.companycatalog.base.BaseViewModel
import com.j7arsen.companycatalog.utils.SingleLiveEvent

class CompanyViewModel : BaseViewModel(){

    private val _openRootScreen : SingleLiveEvent<Unit> = SingleLiveEvent()
    val openRootScreen : LiveData<Unit> = _openRootScreen

    init {
        _openRootScreen.call()
    }

}