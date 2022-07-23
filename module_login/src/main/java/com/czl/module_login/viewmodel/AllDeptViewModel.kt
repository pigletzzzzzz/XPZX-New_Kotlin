package com.czl.module_login.viewmodel

import androidx.databinding.ObservableField
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.DeptBeanItem
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.ToastHelper

class AllDeptViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var page = 1
    var rows = 10
    var name = ObservableField("")
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getAllDeptDetail : SingleLiveEvent<List<DeptBeanItem>> = SingleLiveEvent()
    }


    val onLoadMoreListener: BindingCommand<Void> = BindingCommand(BindingAction {
//        getDeptList()
    })
    val onRefreshListener: BindingCommand<Void> = BindingCommand(BindingAction {
        page = 1
        getDeptList()
    })

    val onSearchChangeCommand:BindingCommand<String> = BindingCommand(BindingConsumer {
        name.set(it)
    })

    val onGoSearchCommand:BindingCommand<Void> = BindingCommand(BindingAction {
        getDeptList()
    })

    fun getDeptList(){
        model.getallDept(page, rows, name.get()!!)
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .doOnSubscribe { showLoading() }
            .subscribe(object :ApiSubscriberHelper<List<DeptBeanItem>>(){
                override fun onResult(t: List<DeptBeanItem>) {
                    dismissLoading()
                    if (t.isNotEmpty()){
                        uc.getAllDeptDetail.postValue(t)
                    }
                }

                override fun onFailed(msg: String?) {
                    dismissLoading()
                    ToastHelper.showErrorToast(msg)
                }
            })
    }

}