package com.j7arsen.companycatalog.base

import com.j7arsen.companycatalog.R

abstract class BaseContainerActivity : BaseActivity(){

    override val layoutId: Int
        get() = R.layout.activity_container

}