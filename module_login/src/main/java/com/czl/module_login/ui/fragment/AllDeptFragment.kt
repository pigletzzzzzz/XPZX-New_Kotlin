package com.czl.module_login.ui.fragment

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.DeptBeanItem
import com.czl.lib_base.event.LiveBusCenter
import com.czl.module_login.BR
import com.czl.module_login.R
import com.czl.module_login.databinding.AllDeptFragmentBinding
import com.czl.module_login.ui.adapter.DeptItemAdapter
import com.czl.module_login.viewmodel.AllDeptViewModel

@Route(path = AppConstants.Router.Login.F_DEPT)
class AllDeptFragment:BaseFragment<AllDeptFragmentBinding,AllDeptViewModel>() {

    private lateinit var deptItemAdapter: DeptItemAdapter

    override fun initContentView(): Int {
        return R.layout.all_dept_fragment
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.apply {
            tvTitle.set("部门列表")
        }
        viewModel.getDeptList()

        iniAdapter()
    }

    override fun reload() {
        super.reload()
        binding.smartRl.autoRefresh()
    }

    override fun initViewObservable() {
        viewModel.uc.getAllDeptDetail.observe(this,{
            if (it.isNotEmpty()){
                deptItemAdapter.setDiffNewData(it as MutableList<DeptBeanItem>?)
            }else{
                val emptyView = View.inflate(context, com.czl.lib_base.R.layout.common_empty_layout, null)
                emptyView.findViewById<ViewGroup>(com.czl.lib_base.R.id.ll_empty).setOnClickListener {
                    viewModel.uc.getAllDeptDetail
                }
                deptItemAdapter.setEmptyView(emptyView)
            }
        })

        deptItemAdapter.setOnItemClickListener { adapter, view, position ->
            var deptBeanItem = adapter.getItem(position) as DeptBeanItem
            LiveBusCenter.postDeptSelectSuccessEvent(deptBeanItem.name,deptBeanItem.id)
            viewModel.finish()
        }
    }

    fun iniAdapter(){
        deptItemAdapter = DeptItemAdapter(this)
        deptItemAdapter.setDiffCallback(deptItemAdapter.diffUtil)
        binding.shRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            adapter = deptItemAdapter
        }
    }
}