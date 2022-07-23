package com.czl.module_login.viewmodel

import androidx.databinding.ObservableField
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.SpHelper
import com.czl.lib_base.util.ToastHelper

class RegisterViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    val uc = UiChangeEvent()
    var unit = ObservableField("")
    var unitId = ObservableField("")
    var phone = ObservableField("")
    var code = ObservableField("")
    var pass = ObservableField("")
    var confirmPass = ObservableField("")

    class UiChangeEvent {
        val getRegisterCodeEvent = SingleLiveEvent<Void>()
    }

    val onUnitChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        unit.set(it)
    })

    val onPhoneChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        phone.set(it)
    })

    val onCodeChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        code.set(it)
    })

    val onPassChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        pass.set(it)
    })

    val onConfirmPassChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        confirmPass.set(it)
    })

    val onGetPhoneCodeCommand:BindingCommand<String> = BindingCommand(BindingAction {
        getRegisterCode()
    })

    val onGoRegisterCommand: BindingCommand<String> = BindingCommand(BindingAction {
        goRegister()
    })

    val onGoUnitDetailCommand:BindingCommand<Void> = BindingCommand(BindingAction {
        startContainerActivity(AppConstants.Router.Login.F_DEPT)
    })

    fun getRegisterCode() {
        if (phone.get().isNullOrEmpty()){
            ToastHelper.showErrorToast("手机号不能为空！")
            return
        }
        model.getRegisterCode(phone.get()!!)
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<String>>() {
                override fun onResult(t: BaseBean<String>) {
                    if (t.success == true) {
                        ToastHelper.showSuccessToast("获取验证码成功！")
                        uc.getRegisterCodeEvent.call()
                    } else {
                        ToastHelper.showErrorToast(t.msg)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                }
            })
    }

    fun goRegister() {
        if (unit.get().isNullOrEmpty()){
            ToastHelper.showErrorToast("单位不能为空！")
            return
        }

        if (phone.get().isNullOrEmpty()){
            ToastHelper.showErrorToast("手机号不能为空！")
            return
        }

        if (code.get().isNullOrEmpty()){
            ToastHelper.showErrorToast("验证码不能为空！")
            return
        }

        if (pass.get().isNullOrEmpty()){
            ToastHelper.showErrorToast("密码不能为空！")
            return
        }

        if (confirmPass.get() != pass.get()){
            ToastHelper.showErrorToast("两次输入的密码不一致！")
            return
        }

        val map = mapOf<String, String>(
            "account" to phone.get()!!,
            "pwd" to pass.get()!!,
            "phone" to phone.get()!!,
            "flag" to "1",
            "randCode" to code.get()!!,
            "deptId" to unitId.get()!!
        )
        model.getRegisterAccount(map)
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .doOnSubscribe { showLoading() }
            .subscribe(object :ApiSubscriberHelper<BaseBean<String>>(){
                override fun onResult(t: BaseBean<String>) {
                    dismissLoading()
                    if (t.success == true){
                        SpHelper.encode(AppConstants.SpKey.APP_ACCOUNT,phone.get()!!)
                        SpHelper.encode(AppConstants.SpKey.APP_PASSWORD,pass.get()!!)
                        finish()
                    }else{
                        showErrorToast(t.msg)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                }
            })
    }
}