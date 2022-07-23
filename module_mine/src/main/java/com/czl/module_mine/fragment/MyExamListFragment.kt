package com.czl.module_mine.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.module_mine.BR
import com.czl.module_mine.R
import com.czl.module_mine.databinding.FragmentMyExamListBinding
import com.czl.module_mine.viewmodel.MyExamListViewModel

/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 16:01

 * 描述:

 */
@Route(path = AppConstants.Router.Mine.F_MY_EXAM_LIST)
class MyExamListFragment : BaseFragment<FragmentMyExamListBinding,MyExamListViewModel>(){

    var examType = 0  //0 全部考试 1进行中 2未开始 3已结束


    companion object{
        fun getInstance(type:Int):MyExamListFragment = MyExamListFragment().apply {
            arguments = Bundle().apply {
                putInt(AppConstants.BundleKey.EXAM_TYPE,type)
            }
        }
    }

    override fun initContentView(): Int {
        return R.layout.fragment_my_exam_list
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {
        examType = arguments?.getInt(AppConstants.BundleKey.EXAM_TYPE,0)!!

        viewModel.status.set(examType.toString())
        viewModel.getMyExam()
    }

}