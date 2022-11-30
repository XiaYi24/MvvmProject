package com.etc.network.netstatus

/**
 * @author :Create by Xia燚
 * 时间：2022/6/16
 * 邮箱：XiahaotianV@163.com
 * 网络状态类型
 **/
@Target(AnnotationTarget.TYPE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class NetType {

    companion object {
        // wifi
        const val WIFI = "WIFI"

        // 手机网络
        const val MOBILE_NET = "NET"

        // 未识别网络
        const val NET_UNKNOWN = "NET_UNKNOWN"

        // 没有网络
        const val NONE = "NONE"
    }
}