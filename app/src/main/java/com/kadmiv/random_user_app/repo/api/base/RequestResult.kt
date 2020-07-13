package com.kadmiv.random_user_app.repo.api.base

sealed class RequestResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : RequestResult<T>()
    data class Error<out T : Any>(val error: T) : RequestResult<T>()
}