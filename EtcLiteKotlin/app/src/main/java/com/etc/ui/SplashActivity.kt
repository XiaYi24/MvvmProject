package com.etc.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.*
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import com.etc.MainActivity
import com.etc.R
import com.etc.base.BaseActivity
import com.etc.databinding.ActivitySplashBinding
import com.etc.dialog.PasswordDialogOne
import com.etc.ext.showToast
import com.etc.ui.school.SchoolActivity
import com.etc.utils.DateUtils
import me.jessyan.autosize.AutoSizeCompat


/**
 * @Description: 欢迎页面
 * @author Xia燚
 * @date 2022/11/11 10:34
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>(),
    PasswordDialogOne.PasswordDialogConfirmListener {

    var mHits = LongArray(5)
    private var only = false


    private var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {

                toTesting()


            }
        }
    }


    override fun initView(savedInstanceState: Bundle?) {
        countDown()
        binding.splashWelcomeImg.setImageResource(R.mipmap.welcome)
        binding.toSetting.setOnClickListener {
            clickMore()
        }

    }


    private fun clickMore() {
        //每点击一次 实现左移一格数据
        System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
        //给数组的最后赋当前时钟值
        //给数组的最后赋当前时钟值
        mHits[mHits.size - 1] = SystemClock.uptimeMillis()
        //当0出的值大于当前时间-500时  证明在500秒内点击了2次
        if (mHits[0] > SystemClock.uptimeMillis() - 2000) {
            //确保只有一个弹窗
            if (!only) {
                only = true
                showPassWordDialog()
            }
        }
    }

    /**
     * 显示密码弹窗
     * */
    private fun showPassWordDialog() {
        handler.removeMessages(1)
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources());
        PasswordDialogOne(this@SplashActivity).setPasswordDialogConfirmListener(this)?.show()
    }




    override fun onConfirmListener(pwd: CharSequence?) {
        //  时间排序

        val currentTime: String = DateUtils.getCurrentTimeX()
        val builder = StringBuilder()
        builder.append(currentTime.split(":".toRegex()).toTypedArray()[0])
        builder.append(currentTime.split(":".toRegex()).toTypedArray()[1])
        if (builder.toString() == pwd) {
            val intent1 = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            startActivityForResult(intent1, 1)
        } else {
            showToast("密码错误")
            only = false
            handler.sendEmptyMessageDelayed(1, (7 * 1000).toLong())
        }

    }

    private fun countDown() {
        handler.sendEmptyMessageDelayed(1, (3 * 1000).toLong())
    }

    private fun toTesting() {
        val intent = Intent(this@SplashActivity, SchoolActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        countDown()
    }


    override fun onRestart() {
        super.onRestart()
        countDown()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeMessages(1)
    }


    override fun observeViewModel() {

    }


}