package com.etc.network

import android.app.Application
import android.content.Context
import android.net.NetworkRequest
import androidx.startup.Initializer
import com.etc.ext.connectivityManager
import com.etc.lifecycle.LifecycleCallBack

val appContext: Application by lazy { BaseInitializer.app }

/**
 * @author :Create by Xia燚
 * 时间：2022/6/16
 * 邮箱：XiahaotianV@163.com
 **/
class BaseInitializer : Initializer<Unit> {
    internal companion object {
       open lateinit var app: Application
    }

    override fun create(context: Context) {
        app = context.applicationContext as Application
        app.registerActivityLifecycleCallbacks(LifecycleCallBack())

//        //监听网络变化状态
        appContext.connectivityManager?.registerNetworkCallback(NetworkRequest.Builder().build(), NetworkStateCallback(app))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}