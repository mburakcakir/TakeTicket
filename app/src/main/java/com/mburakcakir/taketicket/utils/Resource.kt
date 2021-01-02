package com.mburakcakir.taketicket.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> loading(): Resource<T> =
            Resource(status = Status.LOADING, data = null, message = "Loading...")

        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(): Resource<T> =
            Resource(status = Status.ERROR, data = null, message = "Error")

    }
}