package com.mburakcakir.taketicket.utils

sealed class Resource<out T>(val status: Status, val data: T?, val message: Throwable?) {

    class Loading<T> : Resource<T>(status = Status.LOADING, data = null, message = null)
    class Error<T>(exception: Throwable) :
        Resource<T>(status = Status.ERROR, data = null, message = exception)

    class Success<T>(data: T?) : Resource<T>(status = Status.SUCCESS, data = data, message = null)

}

//sealed class Resource<out T : Any> {
//
//    data class Success<out T : Any>(val data: T) : Resource<T>()
//    data class Error(val exception: Exception) : Resource<Nothing>()
//    data class Loading(val message : String) : Resource<String>()
//
//    override fun toString(): String {
//        return when (this) {
//            is Success<*> -> "Success[data=$data]"
//            is Error -> "Error[exception=$exception]"
//            is Loading -> message
//        }
//    }
//}