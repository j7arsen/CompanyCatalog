package com.j7arsen.companycatalog.utils.error

import com.j7arsen.companycatalog.R
import com.j7arsen.companycatalog.utils.ResourceProvider
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandler(private val resourceProvider: ResourceProvider) {

    private val defaultErrorMessage = resourceProvider.getString(R.string.message_error_default)

    fun getError(throwable: Throwable): ErrorData {
        return if (throwable is UnknownHostException || throwable is ConnectException) {
                ErrorData(
                    ErrorData.ERROR_NETWORK,
                    resourceProvider.getString(R.string.message_error_connect))
        } else if (throwable is SocketTimeoutException) {
            ErrorData(
                ErrorData.ERROR_SOCKET_TIMEOUT,
                resourceProvider.getString(R.string.message_error_socket)
            )
        } else if (throwable is HttpException) {
            val message = if (throwable.response() != null) {
                if (throwable.response()!!.message() != null) {
                    throwable.response()!!.code()
                        .toString() + " " + throwable.response()!!.message()
                } else {
                    defaultErrorMessage
                }
            } else {
                defaultErrorMessage
            }
            ErrorData(ErrorData.ERROR_HTTP, message)
        } else {
            ErrorData(ErrorData.ERROR_UNEXPECTED, defaultErrorMessage)
        }
    }

}