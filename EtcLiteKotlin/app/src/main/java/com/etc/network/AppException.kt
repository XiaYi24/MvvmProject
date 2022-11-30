package com.etc.network

/**
 * @author :Create by Xia燚
 * 时间：2022/6/16
 * 邮箱：XiahaotianV@163.com
 * 自定义错误信息异常
 **/
class AppException(
    var msg: String, //错误消息
    var code: String = "0", //错误码
    var errorLog: String = "", //错误日志
    var throwable: Throwable? = null
) : RuntimeException(msg) {

    constructor(msg: String, code: String) : this(msg, code, "", null)

    constructor(error: HoloError, e: Throwable? = null) : this(error.getValue(), error.getKey().toString(), e?.message ?: "", e)

    override fun toString(): String {

        return "AppException(msg='$msg', code=$code )"
    }
}