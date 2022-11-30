package com.etc.network

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
data class ApiResponse<T>(
    val code: Int = 0,
    val data: T,
    val msg: String? = ""
) : BaseResponse<T>() {


    override fun getResponseData() = data

    override fun getResponseCode() = code

    override fun getResponseMsg() = msg ?: ""
}
