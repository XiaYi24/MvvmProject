package com.etc

import android.app.Activity
import android.app.Application
import android.content.res.Configuration
import com.etc.utils.TTSUtils
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.utils.ScreenUtils

/**
 * @author :Create by Xia燚
 * 时间：2022/6/17
 * 邮箱：XiahaotianV@163.com
 **/
@HiltAndroidApp
class XYApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AutoSizeInit()
        MMKV.initialize(this)
        TTSUtils.init(this)
    }

    private fun AutoSizeInit() {
        //屏幕适配监听器1
        AutoSizeConfig.getInstance().onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(target: Any, activity: Activity) {
                //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
                //首先设置最新的屏幕尺寸，ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application !!!
                AutoSizeConfig.getInstance().screenWidth = ScreenUtils.getScreenSize(activity)[0]
                AutoSizeConfig.getInstance().screenHeight = ScreenUtils.getScreenSize(activity)[1]
                //根据屏幕方向，设置设计尺寸
                if (activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    //设置横屏设计尺寸
                    AutoSizeConfig.getInstance()
                        .setDesignWidthInDp(640).designHeightInDp = 400
                } else {
                    //设置竖屏设计尺寸
                    AutoSizeConfig.getInstance()
                        .setDesignWidthInDp(400).designHeightInDp = 640
                }
            }

            override fun onAdaptAfter(target: Any, activity: Activity) {}
        }

    }
}