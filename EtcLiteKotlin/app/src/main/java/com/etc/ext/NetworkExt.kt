package com.etc.ext
import android.util.Log
import com.etc.logger.KLog
import com.etc.network.*
import com.etc.network.netstatus.NetUtils
import com.etc.repository.IRemoteData
import com.etc.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 *
 *
 * @Author holo
 * @Date 2022/3/23
 */

fun IRemoteData.buildRequestBody(vararg params: Pair<String, Any?>): RequestBody {
    val reqObj = JSONObject()

    params.forEach { (key, value) ->
        if (value != null) {
            reqObj.put(key, value)
        }
    }

    return reqObj.toString().toRequestBody("application/json;charset=utf-8".toMediaType())
}

/**
 * 处理响应结果，转为State包裹对象
 *
 * @receiver IRemoteData
 * @param block SuspendFunction0<BaseResponse<R>> 请求函数
 * @return State<R>
 */
inline fun <R> IRemoteData.processCall(block: () -> BaseResponse<R>): State<R> {
    val resp = kotlin.runCatching {
        if (!NetUtils.isConnected()) {
            throw AppException(HoloError.NO_NETWORK)
        }
        block()
    }.getOrElse {
        KLog.e(it)
        val appError = ExceptionHandle.handleException(it)
        return State.error(appError)
    }
    return if (resp.getResponseCode()==200) {
        State.success(resp.getResponseData())
    } else {
        State.error(AppException(resp.getResponseMsg(), resp.getResponseCode().toString()))
    }
}

/**
 * 处理响应结果，不关注请求错误
 *
 * @receiver IRemoteData
 * @param block SuspendFunction0<BaseResponse<R>> 请求函数
 * @return State<R>
 */
inline fun <R> IRemoteData.processCallObj(block: () -> BaseResponse<R>): R? {
    val resp = kotlin.runCatching {
        if (!NetUtils.isConnected()) {
            throw AppException(HoloError.NO_NETWORK)
        }

        block()
    }.getOrElse {
        KLog.e(it)
        return null
    }
    return if (resp.getResponseCode()==200) {
        resp.getResponseData()
    } else {
        null
    }
}



/**
 * 处理响应结果，不关注请求错误
 *
 * @receiver IRemoteData
 * @return State<R>
 */


inline fun <R> IRemoteData.yCallObj(block: () -> BaseResponse<R>): State<R> {
    val resp = kotlin.runCatching {
        if (!NetUtils.isConnected()) {
            throw AppException(HoloError.NO_NETWORK)
        }
        block()
    }.getOrElse {
        KLog.e(it)
        val appError = ExceptionHandle.handleException(it)
        return State.error(appError)
    }
    Log.e("resp", resp.getResponseCode().toString() )
    return if (resp.getResponseCode()==200) {
        State.success(resp.getResponseData())
    } else {
        State.error(AppException(resp.getResponseMsg(), resp.getResponseCode().toString()))
    }
}








inline fun <R> IRemoteData.processListCallX( block: () -> BaseResponse<List<R>>): ListState<R> {
    val resp = kotlin.runCatching {
        if (!NetUtils.isConnected()) {
            return ListState(false, err = AppException(HoloError.NO_NETWORK))
        }
        block()
    }.getOrElse {
        KLog.e(it)
        val appError = ExceptionHandle.handleException(it)
        return ListState(false,  err = appError)
    }

    return if (resp.getResponseCode()==200) {

        return resp.getResponseData().run {
            ListState(true,  resp.getResponseData())
        }
    } else {

        ListState(false,  err = AppException(resp.getResponseMsg(), resp.getResponseCode().toString()))
    }
}



/**
 * Repository直接请求转Flow
 * @receiver IRepository
 * @param dispatcher CoroutineDispatcher
 * @param action SuspendFunction0<State<T>>
 * @return Flow<State<T>>
 */
fun <T> IRepository.toFlow(dispatcher: CoroutineDispatcher, action: suspend () -> State<T>): Flow<State<T>> {
    return flow { emit(action()) }.onStart { emit(State.loading()) }.flowOn(dispatcher)
}