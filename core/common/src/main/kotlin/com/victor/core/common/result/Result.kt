package com.victor.core.common.result

import com.victor.core.common.error.ErrorObject
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed interface Result<out T> {

    data class Success<T>(val data: T) : Result<T>

    data class Error(val error: ErrorObject? = null) : Result<Nothing>

//    data object Loading : Result<Nothing>

}

@OptIn(ExperimentalContracts::class)
inline fun <R, T> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (error: ErrorObject?) -> R
): R {
    contract {
        callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Result.Success -> onSuccess(this.data)
        is Result.Error -> onFailure(this.error)
    }
}