package com.czl.module_mine.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.MineUserInfoBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.route.RouteCenter
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.SpHelper
import com.czl.lib_base.util.ToastHelper

class MineViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getUserInfoData : SingleLiveEvent<MineUserInfoBean> = SingleLiveEvent()
    }

    var getMineUserInfo:BindingCommand<Void> = BindingCommand(BindingAction {
        getMineUserInfo()
    })

    val goLoginClick:BindingCommand<Void> = BindingCommand(BindingAction {
        startContainerActivity(AppConstants.Router.Login.F_LOGIN)
    })

    val goSetingClick:BindingCommand<Void> = BindingCommand(BindingAction {
        if (SpHelper.decodeBoolean(AppConstants.SpKey.IS_LOGIN,false)){
            startContainerActivity(AppConstants.Router.Mine.F_SET)
        }else{
            ToastHelper.showErrorToast("请先登录！")
        }
    })

    val goMyCoursesCommand:BindingCommand<Void> = BindingCommand(BindingAction {
        if (SpHelper.decodeBoolean(AppConstants.SpKey.IS_LOGIN,false)){
            startContainerActivity(AppConstants.Router.Mine.F_MY_COURSES)
        }else{
            ToastHelper.showErrorToast("请先登录！")
        }

    })

    val goMyQuestionBankCommand:BindingCommand<Void> = BindingCommand(BindingAction {
        if (SpHelper.decodeBoolean(AppConstants.SpKey.IS_LOGIN,false)){
            startContainerActivity(AppConstants.Router.Mine.F_MY_QUESTION_BANK)
        }else{
            ToastHelper.showErrorToast("请先登录！")
        }
    })

    fun getMineUserInfo(){
        model.mineUserInfo().compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<MineUserInfoBean>>(){
                override fun onResult(t: BaseBean<MineUserInfoBean>) {
                    if (t.success == true){
                        t.data.apply {
                            uc.getUserInfoData.postValue(this)
                        }

                        SpHelper.encode(AppConstants.SpKey.IS_LOGIN,true)
                    }else{
                        SpHelper.encode(AppConstants.SpKey.IS_LOGIN,false)
                        uc.getUserInfoData.postValue(null)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                    SpHelper.encode(AppConstants.SpKey.IS_LOGIN,false)
                    uc.getUserInfoData.postValue(null)
                }
            })
    }
}