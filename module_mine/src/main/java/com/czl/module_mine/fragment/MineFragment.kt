package com.czl.module_mine.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.extension.loadCircleImage
import com.czl.lib_base.util.DayModeUtil
import com.czl.lib_base.util.SpHelper
import com.czl.module_mine.BR
import com.czl.module_mine.R
import com.czl.module_mine.databinding.FragmentMineBinding
import com.czl.module_mine.viewmodel.MineViewModel
import com.gyf.immersionbar.ImmersionBar

@Route(path = AppConstants.Router.Mine.F_MINE)
class MineFragment:BaseFragment<FragmentMineBinding,MineViewModel>() {
    override fun initContentView(): Int {
        return R.layout.fragment_mine
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun onSupportVisible() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)
            .statusBarColor(com.czl.lib_base.R.color.base_color_2e7bf7)
            .fitsSystemWindows(true)
            .init()
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {
    }

    override fun initViewObservable() {
        viewModel.uc.getUserInfoData.observe(this,{
            if (it != null){
                it.apply {
                    binding.userData = this
                    binding.tvUserName.text = userInfo.userName
                    binding.tvLoginDay.text = "${loginday}天"
                    binding.tvLearningMinutes.text = "${daystudy/60}分钟"
                    if (userInfo.portait.isNullOrEmpty()){
                        binding.imgHeard.setBackgroundResource(R.mipmap.default_mine)
                    }else{
                        binding.imgHeard.loadCircleImage(AppConstants.Url.IMG_UPLOAD_URL+userInfo.portait)
                    }
                    if (examCount == 0){
                        binding.tvWdksNum.visibility = View.GONE
                    }else{
                        binding.tvWdksNum.text = examCount.toString()
                        binding.tvWdksNum.visibility = View.VISIBLE
                    }

                    if (msgNum.isNullOrEmpty() || msgNum == "0"){
                        binding.tvWdxNum.visibility = View.GONE
                    }else{
                        binding.tvWdxNum.text = msgNum
                        binding.tvWdxNum.visibility = View.VISIBLE
                    }

                    if (orderNum == 0){
                        binding.tvGwcNum.visibility = View.GONE
                    }else{
                        binding.tvGwcNum.visibility = View.VISIBLE
                        binding.tvGwcNum.text = orderNum.toString()
                    }
                }

            }else{
                binding.tvUserName.text = "请登录"
                binding.tvLoginDay.text = "--"
                binding.tvLearningMinutes.text = "--"
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (SpHelper.decodeBoolean(AppConstants.SpKey.IS_LOGIN,false)){
            viewModel.getMineUserInfo()
        }else{
            binding.imgHeard.setImageResource(R.mipmap.default_mine)
            binding.tvUserName.text = "请登录"
            binding.tvLoginDay.text = "--"
            binding.tvLearningMinutes.text = "--"
        }
    }
}