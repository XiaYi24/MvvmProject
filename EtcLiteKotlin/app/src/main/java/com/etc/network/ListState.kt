package com.etc.network

import com.etc.bean.BaseBean


/**
 * 分页查询数据通用base
 */
data class ListState<T>(
    /**
     * 是否请求成功
     */
    val isSuccess: Boolean,


    /**
     * 列表数据
     */
    val listData: List<T> = listOf(),

    /**
     * 错误信息
     */
    val err: AppException = AppException("")
) : BaseBean()