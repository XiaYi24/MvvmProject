package com.etc.ui.school

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.etc.adapter.AgentAdapter
import com.etc.base.BaseActivity
import com.etc.bean.AgentOperationBean
import com.etc.databinding.ActivitySchoolBinding
import com.etc.ext.showToast
import com.etc.view.spinner.NiceSpinner
import com.etc.view.spinner.OnSpinnerItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Description: 学校Activity
 * @author Xia燚
 * @date 2022/11/14 15:34
 */
@AndroidEntryPoint
class SchoolActivity:BaseActivity<ActivitySchoolBinding>(), OnSpinnerItemSelectedListener {

    private val viewModel: SchoolViewModel by viewModels()
    private lateinit var agentList: List<AgentOperationBean>
    override fun initView(savedInstanceState: Bundle?) {
        binding.agentSpinner.onSpinnerItemSelectedListener = this
//        binding.operatorsSpinner.onSpinnerItemSelectedListener =this
//        binding.schoolsSpinner.onSpinnerItemSelectedListener = this

    }


    override fun initData() {
        viewModel.doAgentList()
    }

    override fun observeViewModel() {
        observerObj(viewModel.agentListResp) { list ->

            if (list.isNullOrEmpty()) {
                showToast("代理商列表为空")
            } else {

                val adapter = AgentAdapter(this, list  )
                binding.agentSpinner.setAdapter(adapter)

            }
        }
    }

    override fun onItemSelected(parent: NiceSpinner?, view: View?, position: Int, id: Long) {

    }

}