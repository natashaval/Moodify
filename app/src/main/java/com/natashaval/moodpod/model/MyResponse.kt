package com.natashaval.moodpod.model

/**
 * Created by natasha.santoso on 14/01/21.
 */
data class MyResponse<out T>(val status: Status, val data: T?, val message: String?) {
  companion object {
    fun <T> success(data: T): MyResponse<T> = MyResponse(Status.SUCCESS, data, null)
    fun <T> failed(message: String?): MyResponse<T> = MyResponse(Status.FAILED, null, message)
    fun <T> error(data: T?, message: String?): MyResponse<T> =
        MyResponse(Status.ERROR, data, message)

    fun <T> loading(data: T? = null): MyResponse<T> = MyResponse(Status.LOADING, data, null)
    fun <T> empty(): MyResponse<T> = MyResponse(Status.EMPTY, null, null)
  }
}

enum class Status { SUCCESS, FAILED, ERROR, LOADING, EMPTY }