package com.czl.module_mine.fragment

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.adapter.ViewPagerFmAdapter
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.module_mine.BR
import com.czl.module_mine.R
import com.czl.module_mine.databinding.FragmentMyExamBinding
import com.czl.module_mine.viewmodel.MyExamViewModel
import com.google.android.material.tabs.TabLayoutMediator

/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 15:44

 * 描述:

 */
@Route(path = AppConstants.Router.Mine.F_MY_EXAM)
class MyExamFragment : BaseFragment<FragmentMyExamBinding, MyExamViewModel>() {
    override fun initContentView(): Int {
        return R.layout.fragment_my_exam
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.tvTitle.set("我的考试")
        initViewPagerFragment()
    }

    private fun initViewPagerFragment() {
        val fragments =
            arrayListOf(
                MyExamListFragment.getInstance(0),
                MyExamListFragment.getInstance(1),
                MyExamListFragment.getInstance(2),
                MyExamListFragment.getInstance(3)
            )
        binding.viewpager.apply {
            adapter = ViewPagerFmAdapter(childFragmentManager, lifecycle, fragments)
            offscreenPageLimit = fragments.size
        }
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "全部"
                1 -> tab.text = "进行中"
                2 -> tab.text = "未开始"
                3 -> tab.text = "已结束"
            }
        }.attach()
        if (arguments?.getInt(AppConstants.BundleKey.GO_EXAM_TYPE, 0)!! > 0){
            binding.viewpager.currentItem = arguments?.getInt(AppConstants.BundleKey.GO_EXAM_TYPE, 0)!!
        }
    }
}