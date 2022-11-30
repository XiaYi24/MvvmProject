package com.etc.ui.school

import com.etc.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Description: SchoolRepository 类
 * @author Xia燚
 * @date 2022/11/14 16:44
 */
class SchoolRepository @Inject constructor(
    private val remote: SchoolRemoteData,
    private val ioDispatcher: CoroutineDispatcher
) : IRepository {

    suspend fun agentList() = flow { emit(remote.doAgentList()) }.flowOn(ioDispatcher)


}