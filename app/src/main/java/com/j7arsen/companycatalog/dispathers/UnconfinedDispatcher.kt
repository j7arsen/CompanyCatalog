package com.j7arsen.companycatalog.dispathers

import com.j7arsen.domain.dispatcher.DispatcherFacade
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class UnconfinedDispatcher : DispatcherFacade{

    override val dispatcher: CoroutineContext
        get() = Dispatchers.Unconfined
}