package com.victor.core.common.error

sealed class ErrorObject {

    object NetworkError : ErrorObject()

    object NotFound : ErrorObject()

    object Unknown : ErrorObject()
}