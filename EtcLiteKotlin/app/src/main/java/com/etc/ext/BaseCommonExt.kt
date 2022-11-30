package com.etc.ext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.etc.BuildConfig
import com.etc.network.AppException


/**
 * dp值转换成px
 *
 * @return
 */
fun Float.dp2px(): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

/**
 * sp值转换成px
 *
 * @return
 */
fun Float.sp2px(): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)

/**
 * dp值转换成px
 *
 * @return
 */
fun Int.dp2px(): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * sp值转换成px
 *
 * @return
 */
fun Int.sp2px(): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * 显示toast
 * @receiver Context
 * @param message CharSequence?
 */
fun Context.showToast(message: CharSequence?) {
    message?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}

/**
 * 显示toast
 * @receiver Context
 * @param message Int
 */
fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showErrToast(err: AppException) {
    val msg = if (BuildConfig.DEBUG) "${err.msg} Code:[${err.code}]" else err.msg
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 * Fragment中显示toast
 * @receiver Fragment
 * @param message CharSequence?
 */
fun Fragment.showToast(message: CharSequence?) {
    activity?.run {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * Fragment中显示toast
 * @receiver Fragment
 * @param message Int
 */
fun Fragment.showToast(@StringRes message: Int) {
    activity?.run {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showErrToast(err: AppException) {
    activity?.run {
        val msg = if (BuildConfig.DEBUG) "${err.msg} Code:[${err.code}]" else err.msg
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

