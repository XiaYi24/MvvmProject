package com.etc.network

/**
 * @author :Create by Xia燚
 * 时间：2022/6/16
 * 邮箱：XiahaotianV@163.com
 **/
enum class HoloError(private val code: Int, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, "请求失败，请稍后再试"),
    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误，请稍后再试"),
    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, "网络连接错误，请稍后重试"),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书出错，请稍后再试"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "网络连接超时，请稍后重试"),

    /**
     * 无网络
     */
    NO_NETWORK(1007,"请检查您的网络连接状态");

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }

}