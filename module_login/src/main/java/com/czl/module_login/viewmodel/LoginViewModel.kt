package com.czl.module_login.viewmodel

import android.os.Bundle
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.czl.lib_base.base.AppManager
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.LoginUserBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.data.bean.UserBean
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.route.RouteCenter
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.SpHelper
import me.yokeyword.fragmentation.SupportFragment


/**
 * @author Alwyn
 * @Date 2020/10/15
 * @Description
 */
class LoginViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var account = ObservableField("")
    var pwd = ObservableField("")
    var endpoint = "android-${DeviceUtils.getModel()}-${AppUtils.getAppVersionName()}"

    val onAccountChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        account.set(it)
    })

    val onPwdChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        pwd.set(it)
    })

    var btnLoginClick: BindingCommand<Any> = BindingCommand(BindingAction {
        loginByPwd()
    })

    val goRegisterClick:BindingCommand<Void> = BindingCommand(BindingAction {
        startContainerActivity(AppConstants.Router.Login.F_REGISTER)
    })


    private fun loginByPwd() {
        if (account.get().isNullOrBlank() || pwd.get().isNullOrBlank()) {
            showNormalToast("账号或密码不能为空")
            return
        }
        model.apply {
            userLogin(account.get()!!, pwd.get()!!,endpoint)
                .compose(RxThreadHelper.rxSchedulerHelper(this@LoginViewModel))
                .doOnSubscribe { showLoading() }
                .subscribe(object : ApiSubscriberHelper<BaseBean<LoginUserBean>>() {
                    override fun onResult(t: BaseBean<LoginUserBean>) {
                        dismissLoading()
                        if (t.success == true) {
                            showSuccessToast("登录成功")

                            SpHelper.encode(AppConstants.SpKey.APP_ACCOUNT,account.get()!!)
                            SpHelper.encode(AppConstants.SpKey.APP_PASSWORD,pwd.get()!!)
                            SpHelper.encode(AppConstants.SpKey.USER_ID, t.data?.id!!)
                            SpHelper.encode(AppConstants.SpKey.IS_LOGIN,true)

                            LiveBusCenter.postLoginSuccessEvent()
                            RouteCenter.navigate(AppConstants.Router.Home.A_MAIN)
                        }
                    }

                    override fun onFailed(msg: String?) {
                        dismissLoading()
                        showNormalToast(msg)
                    }

                })
        }
    }
}