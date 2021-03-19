package com.homecredit.exam.utils

import com.homecredit.exam.utils.Status.ERROR
import com.homecredit.exam.utils.Status.LOADING
import com.homecredit.exam.utils.Status.SUCCESS


data class Result<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Result<T> =
            Result(status = SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Result<T> =
            Result(status = ERROR, data = data, message = message)

        fun <T> loading(data: T?): Result<T> =
            Result(status = LOADING, data = data, message = null)
    }
}