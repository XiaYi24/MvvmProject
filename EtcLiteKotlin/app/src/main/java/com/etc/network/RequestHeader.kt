package com.etc.network

import com.etc.utils.MD5Util
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * @author :Create by Xia燚
 * 时间：2022/6/27
 * 邮箱：XiahaotianV@163.com
 **/
class RequestHeader: Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        val schoolNum = "lLO9mZpNayP%JC01"
        val random = Random()
        val keys = CharArray(8)
        for (i in keys.indices) {
            val choice = if (random.nextInt(2) % 2 == 0) 65 else 97 // 取得大写还是小写
            keys[i] = (choice + random.nextInt(26)).toChar()
        }
        val str = keys.toString() + schoolNum
        val md5String: String = MD5Util.getMD5String(str)

       val request = chain.request().newBuilder()
           .header("Content-Type", "application/json; charset=utf-8")
           .header("random",  keys.toString())
           .header("sign", md5String)
           .build()
        return chain.proceed(request)
    }
}