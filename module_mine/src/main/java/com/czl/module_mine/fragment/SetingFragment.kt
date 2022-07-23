package com.czl.module_mine.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.util.DialogHelper
import com.czl.lib_base.util.SpHelper
import com.czl.module_mine.BR
import com.czl.module_mine.R
import com.czl.module_mine.databinding.FragmentSetingBinding
import com.czl.module_mine.viewmodel.SetingViewModel

@Route(path = AppConstants.Router.Mine.F_SET)
class SetingFragment : BaseFragment<FragmentSetingBinding, SetingViewModel>() {
    override fun initContentView(): Int {
        return R.layout.fragment_seting
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.apply {
            tvTitle.set("设置")
        }
    }

    override fun initViewObservable() {

        viewModel.uc.signOutEvnt.observe(this, {
            DialogHelper.showLoginOutDialog(
                requireContext(),
                "退出登录",
                "是否退出${SpHelper.decodeString(AppConstants.SpKey.APP_ACCOUNT)}的登录"
            ) {
                SpHelper.encode(AppConstants.SpKey.IS_LOGIN,false)
                SpHelper.encode(AppConstants.SpKey.APP_TOOKEN, "")
                viewModel.finish()
            }
        })
    }

}