package com.czl.module_mine.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.MyCoursesBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.ToastHelper

class MyCoursesViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var page = 1
    var rows = 10
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val onMyCoursesEvent:SingleLiveEvent<List<MyCoursesBean.Row>> = SingleLiveEvent()
    }


    val onLoadMoreListener: BindingCommand<Void> = BindingCommand(BindingAction {
        getMuCourses()
    })
    val onRefreshListener: BindingCommand<Void> = BindingCommand(BindingAction {
        page = 1
        getMuCourses()
    })

    fun getMuCourses(){
        model.getMyCourses(page,rows,"3","false")
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object :ApiSubscriberHelper<BaseBean<MyCoursesBean>>(){
                override fun onResult(t: BaseBean<MyCoursesBean>) {
                    if (t.success == true){
                        if (t.data?.grid?.total!! > 0){
                            page++
                            uc.onMyCoursesEvent.postValue(t.data?.grid?.rows)
                        }
                    }else{
                        uc.onMyCoursesEvent.postValue(null)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                }
            })
    }

}