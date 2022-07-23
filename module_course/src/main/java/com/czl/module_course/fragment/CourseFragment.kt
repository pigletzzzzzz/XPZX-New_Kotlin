package com.czl.module_course.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.util.DayModeUtil
import com.czl.module_course.BR
import com.czl.module_course.R
import com.czl.module_course.adapter.CourseItemAdapter
import com.czl.module_course.databinding.FragmentCourseBinding
import com.czl.module_course.viewmodel.CourseViewModel
import com.gyf.immersionbar.ImmersionBar

@Route(path = AppConstants.Router.Course.F_COURSE)
class CourseFragment:BaseFragment<FragmentCourseBinding, CourseViewModel>() {

    private lateinit var courseItemAdapter: CourseItemAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_course
    }

    override fun onSupportVisible() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)
            .statusBarColor(com.czl.lib_base.R.color.white)
            .fitsSystemWindows(true)
            .init()
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {
        viewModel.getCourseListDetail()

        initAdapter()
    }

    override fun reload() {
        super.reload()
        binding.smartRl.autoRefresh()
    }

    override fun initViewObservable() {

        viewModel.uc.getCourseDetail.observe(this,{
            handleRecyclerviewData(
                it == null,
                it as MutableList<*>?,
                courseItemAdapter,
                binding.shRv,
                binding.smartRl,
                viewModel.page,
                it?.isEmpty()
            )
        })
    }

    private fun initAdapter(){
        courseItemAdapter = CourseItemAdapter(this)
        courseItemAdapter.setDiffCallback(courseItemAdapter.diffUtil)
        binding.shRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            adapter = courseItemAdapter
            showShimmerAdapter()
        }
    }
}