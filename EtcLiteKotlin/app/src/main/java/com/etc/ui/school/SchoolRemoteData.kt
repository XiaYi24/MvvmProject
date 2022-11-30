package com.etc.ui.school

import com.etc.api.ApiService
import com.etc.bean.AgentOperationBean
import com.etc.ext.buildRequestBody
import com.etc.ext.processCallObj
import com.etc.repository.IRemoteData
import javax.inject.Inject

/**
 * @Description: SchoolRemoteData 建立网络请求
 * @author Xia燚
 * @date 2022/11/14 17:21
 */
class SchoolRemoteData @Inject constructor(private val service: ApiService) : IRemoteData {

    suspend fun doAgentList(): List<AgentOperationBean>? = processCallObj { service.doAgent( buildRequestBody("name" to "SY")) }


}