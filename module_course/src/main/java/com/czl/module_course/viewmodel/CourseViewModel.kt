package com.czl.module_course.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.CourseListBean
import com.czl.lib_base.data.bean.CourseTabItemBeanItem
import com.czl.lib_base.data.bean.HomeDetailBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

class CourseViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var page = 1
    var rows = 10
    var title = ""
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getCourseDetail : SingleLiveEvent<List<CourseListBean.Row>> = SingleLiveEvent()
    }


    val onLoadMoreListener: BindingCommand<Void> = BindingCommand(BindingAction {
        getCourseListDetail()

    })
    val onRefreshListener: BindingCommand<Void> = BindingCommand(BindingAction {
        page = 1
        getCourseListDetail()
    })


    fun getCourseListDetail(){
        model.courseListDetail(page, rows, title)
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object :ApiSubscriberHelper<BaseBean<CourseListBean>>(loadService){
                override fun onResult(t: BaseBean<CourseListBean>) {
                    if (t.success == true){
                        page++
                        uc.getCourseDetail.postValue(t.data?.grid?.rows)
                    }else{
                        uc.getCourseDetail.postValue(null)
                    }
                }

                override fun onFailed(msg: String?) {
                    uc.getCourseDetail.postValue(null)
                }
            })
    }

}