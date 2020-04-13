package com.j7arsen.companycatalog.utils.error

data class ErrorData(val errorType : Int, val errorMessage : String, val errorHttpCode : Int? = null, val errorCode : Int? = null) {

    companion object{
        const val ERROR_NETWORK = 100001
        const val ERROR_SOCKET_TIMEOUT = 100002
        const val ERROR_UNEXPECTED = 100003
        const val ERROR_HTTP = 100004
    }

}