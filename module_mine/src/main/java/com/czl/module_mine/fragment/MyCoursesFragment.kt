package com.czl.module_mine.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.module_mine.BR
import com.czl.module_mine.R
import com.czl.module_mine.adapter.MyCoursesItemAdapter
import com.czl.module_mine.databinding.FragmentMyCoursesBinding
import com.czl.module_mine.viewmodel.MyCoursesViewModel

@Route(path = AppConstants.Router.Mine.F_MY_COURSES)
class MyCoursesFragment:BaseFragment<FragmentMyCoursesBinding,MyCoursesViewModel>(){

    private lateinit var myCoursesItemAdapter: MyCoursesItemAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_my_courses
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
        viewModel.apply {
            tvTitle.set("我的课程")
        }
        viewModel.getMuCourses()

        iniAdapter()
    }

    override fun reload() {
        super.reload()
        binding.smartRl.autoRefresh()
    }

    override fun initViewObservable() {
        viewModel.uc.onMyCoursesEvent.observe(this,{
            handleRecyclerviewData(
                it == null,
                it as MutableList<*>?,
                myCoursesItemAdapter,
                binding.shRv,
                binding.smartRl,
                viewModel.page,
                it?.isEmpty()
            )
        })
    }

    private fun iniAdapter(){
        myCoursesItemAdapter = MyCoursesItemAdapter(this)
        myCoursesItemAdapter.setDiffCallback(myCoursesItemAdapter.diffUtil)
        binding.shRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            adapter = myCoursesItemAdapter
            showShimmerAdapter()
        }
    }

}