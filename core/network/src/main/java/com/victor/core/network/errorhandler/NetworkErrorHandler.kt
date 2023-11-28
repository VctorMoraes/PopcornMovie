package com.victor.core.network.errorhandler

import com.victor.core.common.error.ErrorObject

interface NetworkErrorHandler {

    fun getError(throwable: Throwable): ErrorObject
}