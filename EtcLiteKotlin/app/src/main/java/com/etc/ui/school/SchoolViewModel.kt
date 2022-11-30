package com.etc.ui.school

import androidx.lifecycle.viewModelScope
import com.etc.base.BaseViewModel
import com.etc.bean.AgentOperationBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Description: (用一句话描述)
 * @author Xia燚
 * @date 2022/11/14 16:38
 */
@HiltViewModel
class SchoolViewModel @Inject constructor(  private val repo: SchoolRepository) : BaseViewModel() {
    //获取代理商
    private val _agentListResp = MutableSharedFlow<List<AgentOperationBean>>()
    val agentListResp = _agentListResp.asSharedFlow()

    fun doAgentList() {
        viewModelScope.launch {
            repo.agentList().collect{
                _agentListResp.emit(it ?: emptyList())
            }
        }
    }
}