package com.etc.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.etc.ext.showErrToast
import com.etc.network.ListState
import com.etc.network.NetworkStateManager
import com.etc.network.State
import com.etc.network.netstatus.NetType
import com.etc.utils.ViewBindingUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions


/**
 * @author :Create by Xia燚
 * 时间：2022/6/16
 * 邮箱：XiahaotianV@163.com
 * activity 基类
 **/
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
        binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)
        initView(savedInstanceState)
        observeViewModel()
        NetworkStateManager.mNetworkStateCallback.observe(this) { netType ->
            onNetworkStateChanged(netType)
        }
        initData()
        requestPermission()
    }







    /**
     * 隐藏状态栏
     */
    private fun initWindow() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
       // window.insetsController?.hide(WindowInsets.Type.statusBars());


        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val attributes = window.attributes
            attributes.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            window.attributes = attributes
        }
    }

    /**
     * 创建LiveData数据观察者
     */
    abstract fun observeViewModel()

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netType: @NetType String) {}


    open fun initData() {}


    protected fun <T> observer(
        flow: Flow<State<T>>,
        success: (t: T) -> Unit,
        error: ((code: String) -> Unit)? = null
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    when (state) {
                        is State.Success -> {
                            hideLoading()
                            success.invoke(state.data)
                        }
                        is State.Error -> {
                            hideLoading()
                            showErrToast(state.err)
                            error?.invoke(state.err.code)
                        }
                    }
                }
            }
        }
    }

    protected fun <T> observer(
        flow: Flow<State<T>>,
        loading: () -> Unit,
        success: (t: T) -> Unit,
        error: (() -> Unit)? = null
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    when (state) {
                        is State.Success -> {

                            success.invoke(state.data)
                        }
                        is State.Error -> {

                            showErrToast(state.err)
                            error?.invoke()
                        }
                    }
                }
            }
        }
    }

    protected fun <T> observer(flow: Flow<State<T>>, action: (t: T?) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    when (state) {
                        is State.Success -> action.invoke(state.data)
                        is State.Error -> {
                            showErrToast(state.err)
                            action.invoke(null)
                        }

                    }
                }
            }
        }
    }

    protected fun <T> observerObj(flow: Flow<T>, action: (t: T?) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { action.invoke(it) }

            }
        }
    }

    protected fun <T> observerList(flow: Flow<ListState<T>>, action: (st: ListState<T>) -> Unit) {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    if (state.isSuccess){
                        action.invoke(state)
                    }else{
                        showErrToast(state.err)
                    }

                }
            }
        }
    }


    /**权限*/
    private fun requestPermission() {
        EasyPermissions.requestPermissions(
            this,
            "申请权限",
            0,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    /**
     * 权限检查
     *
     * @param neededPermissions 需要的权限
     * @return 是否全部被允许
     */
    protected open fun checkPermissions(neededPermissions: Array<String?>?): Boolean {
        if (neededPermissions == null || neededPermissions.isEmpty()) {
            return true
        }
        var allGranted = true
        for (neededPermission in neededPermissions) {
            allGranted = allGranted and (ContextCompat.checkSelfPermission(
                this,
                neededPermission!!
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return allGranted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    private fun sHowLoading() {
//        if (loadingDialog == null) {
//            loadingDialog = MaterialDialog(this, TransBehavior)
//                .customView(R.layout.dialog_loading)
//                .cancelOnTouchOutside(false)
//                .cancelable(false)
//        }
//        if (loadingDialog?.isShowing != true) {
//            dialogJob?.cancel()
//            dialogJob = lifecycleScope.launch {
//                delay(1000)
//                loadingDialog?.show()
//                delay(5000)
//                KLog.d("Holo", "5s超时隐藏")
//                hideLoading()
//            }
//        }
    }

    private fun hideLoading() {
//        dialogJob?.cancel()
//        dialogJob = null
//        if (isDestroyed || isFinishing) return
//        loadingDialog?.dismiss()
    }

}