package com.victor.core.network.errorhandler

import com.victor.core.common.error.ErrorObject
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class NetworkErrorHandlerImpl @Inject constructor() : NetworkErrorHandler {

    override fun getError(throwable: Throwable): ErrorObject {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorObject.NotFound
                    else -> ErrorObject.NetworkError
                }
            }

            is IOException -> {
                ErrorObject.NetworkError
            }

            else -> {
                ErrorObject.Unknown
            }
        }
    }
}