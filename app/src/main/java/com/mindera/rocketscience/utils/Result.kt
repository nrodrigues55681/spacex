package com.mindera.rocketscience.utils

sealed class Result<out T> {

    companion object Factory {
        fun <T> success(data: T): Result<T> = Success(data)

        fun <T> error(message: String?): Result<T> = Error(message)

        fun <T> loading(): Result<T> = Loading
    }

    data class Success<out T>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()
    data class Error<out T>(val message: String?): Result<T>()
}