package com.etc.api

import com.etc.bean.AgentOperationBean
import com.etc.network.ApiResponse
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @Description: ApiService
 * @author Xia燚
 * @date 2022/11/10 17:22
 */

interface ApiService {


    /**
     * 心跳
     */
    @GET
    suspend fun doHeartBeat(@Url s: String): ApiResponse<String?>


    /**
     * 获取运营商
     */
    @POST(BaseApi.GET_AGENT)
    suspend fun doAgent(
        @Body requestBody: RequestBody
    ): ApiResponse<List<AgentOperationBean>>


//
//    /**
//     * 检验时间
//     */
//    @GET
//    suspend fun doTimeVerify(@Url s: String): ApiResponse<String?>
//
//
//    /**
//     * 查询设备状态
//     * */
//    @POST(WanApi.API_GET_DEVICE)
//    suspend fun doDeviceState(
//        @Body requestBody: RequestBody
//    ): ApiResponse<DeviceBean>
//
//    /**
//     * 设备绑定激活码接口  dorRegister
//     */
//    @POST(WanApi.API_BIND_DEVICE)
//    suspend fun dorRegister(
//        @Body requestBody: RequestBody
//    ): ApiResponse<RegisterBean>
//
//    /**
//     * 下载列表请求
//     */
//    @POST(WanApi.API_GET_VERSIONS)
//    suspend fun getVersionsList(
//        @Body requestBody: RequestBody
//    ): ApiResponse<List<DownloadBean>>
//
//
//
//    /**
//     *  通过Code 获取下载的URl
//     */
//    @POST(WanApi.API_GET_VERSION)
//    suspend fun getVersionsUrl(
//        @Body requestBody: RequestBody
//    ): ApiResponse<DownloadUrlBean>
//
//
//
//
//
//    /**
//     * 请求学校列表
//     */
//    @POST(WanApi.API_LOGIN_X)
//    suspend fun doTest(
//        @HeaderMap headers: Map<String, String>,
//        @Body requestBody: RequestBody
//    ): ApiResponse<List<SchoolBean>>
//
//    @POST(WanApi.API_LOGIN_X)
//    suspend fun doTestX(
//        @Body requestBody: RequestBody
//    ): ApiResponse<List<SchoolBean>>
//
//
//    @POST(WanApi.API_GET_MANAGERS)
//    suspend fun doUserList(
//        @Body requestBody: RequestBody
//    ): ApiResponse<List<UserBean>>
}