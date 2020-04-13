package com.j7arsen.companycatalog.dispathers

import com.j7arsen.domain.dispatcher.DispatcherFacade
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MainDispatcher : DispatcherFacade{

    override val dispatcher: CoroutineContext
        get() = Dispatchers.Main
}