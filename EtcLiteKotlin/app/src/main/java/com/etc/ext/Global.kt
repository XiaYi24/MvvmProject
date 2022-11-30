package com.etc.ext
import android.view.View


/**
 * @author :creat by Xia燚
 * 时间：2022/5/17
 * 邮箱：XiahaotianV@163.com
 **/

fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {

    val listener = View.OnClickListener { it.block() }

    v.forEach { it?.setOnClickListener(listener) }
}

