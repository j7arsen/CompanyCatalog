package com.j7arsen.domain.dispatcher

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface DispatcherFacade{

    val dispatcher : CoroutineContext

}