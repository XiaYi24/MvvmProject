package com.etc.network

/**
 * @Desc 服务器返回数据的基类
 * 必须实现抽象方法，根据自己的业务判断返回请求结果是否成功
 *
 * @Author holo
 * @Date 2022/1/11
 */
abstract class BaseResponse<T> {

    abstract fun getResponseData(): T

    abstract fun getResponseCode(): Int

    abstract fun getResponseMsg(): String
}