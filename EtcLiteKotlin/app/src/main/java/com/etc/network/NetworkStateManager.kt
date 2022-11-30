package com.etc.network

import com.etc.network.netstatus.NetType
import com.kunminx.architecture.ui.callback.UnPeekLiveData

object NetworkStateManager {

    val mNetworkStateCallback = UnPeekLiveData<@NetType String>()

    var netType: String = NetType.NONE
}