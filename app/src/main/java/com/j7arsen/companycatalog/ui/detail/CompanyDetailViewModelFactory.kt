package com.j7arsen.companycatalog.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CompanyDetailViewModelFactory(private val companyId : String?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CompanyDetailViewModel(companyId) as T
    }
}