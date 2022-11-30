package com.etc.bean

import com.google.gson.Gson

open class BaseBean {

    /**
     * 重写toString方法，对象直接打印json串
     * @return String
     */
    override fun toString(): String {
        return Gson().toJson(this)
    }
}