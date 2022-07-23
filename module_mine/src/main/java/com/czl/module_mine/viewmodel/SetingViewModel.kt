package com.czl.module_mine.viewmodel

import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.util.SpHelper
import com.czl.lib_base.util.ToastHelper

class SetingViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    val uc = UiChangeEvent()

    class UiChangeEvent {
        val signOutEvnt = SingleLiveEvent<Void>()
    }

    //个人信息
    val goUserInfoClick: BindingCommand<Void> = BindingCommand(BindingAction {
        if (SpHelper.decodeBoolean(AppConstants.SpKey.IS_LOGIN,false)){
            startContainerActivity(AppConstants.Router.Mine.F_USER_INFO)
        }else{
            ToastHelper.showErrorToast("请先登录账号！")
        }
    })

    //修改密码
    val goChangePasswordClick: BindingCommand<Void> = BindingCommand(BindingAction {
        if (SpHelper.decodeBoolean(AppConstants.SpKey.IS_LOGIN,false)){
        }else{
            ToastHelper.showErrorToast("请先登录账号！")
        }
    })

    //用户协议
    val goUserAgreementClick: BindingCommand<Void> = BindingCommand(BindingAction {  })

    //隐私政策
    val goPrivacyPolicyClick: BindingCommand<Void> = BindingCommand(BindingAction {  })

    val signOutClick:BindingCommand<Void> = BindingCommand(BindingAction {
        uc.signOutEvnt.call()
    })

}