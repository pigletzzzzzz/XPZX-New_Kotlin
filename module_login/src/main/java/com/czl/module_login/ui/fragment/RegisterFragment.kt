package com.czl.module_login.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.event.LiveBusCenter
import com.czl.module_login.BR
import com.czl.module_login.R
import com.czl.module_login.databinding.RegisterFragmentBinding
import com.czl.module_login.viewmodel.RegisterViewModel
import com.gyf.immersionbar.ImmersionBar
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

@Route(path = AppConstants.Router.Login.F_REGISTER)
class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>() {
    override fun initContentView(): Int {
        return R.layout.register_fragment
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun onSupportVisible() {
        ImmersionBar.with(this).statusBarDarkFont(false).init()
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initViewObservable() {
        viewModel.uc.getRegisterCodeEvent.observe(this, {
            Flowable.intervalRange(0, 120, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    binding.tvGetCode.isEnabled = false
                    binding.tvGetCode.isClickable = false
                    binding.tvGetCode.text = "${120 - it}秒"
                }
                .doOnComplete {
                    binding.tvGetCode.text = "获取验证码"
                    binding.tvGetCode.isEnabled = true
                    binding.tvGetCode.isClickable = true
                }
                .subscribe()
        })

        LiveBusCenter.observeDeptSelectEvent(this,{
            viewModel.unit.set(it.name)
            viewModel.unitId.set(it.id)
        })
    }
}