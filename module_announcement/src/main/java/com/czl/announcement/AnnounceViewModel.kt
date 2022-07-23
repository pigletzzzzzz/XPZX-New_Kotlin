package com.czl.announcement

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.CourseListBean
import com.czl.lib_base.data.bean.NoticeBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

class AnnounceViewModel(application: MyApplication,model:DataRepository):BaseViewModel<DataRepository>(application, model) {

    var page = 1
    var rows = 10
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getNoticeDetail : SingleLiveEvent<List<NoticeBean.Row>> = SingleLiveEvent()
    }

    val onLoadMoreListener: BindingCommand<Void> = BindingCommand(BindingAction {
        getNoticeList()

    })
    val onRefreshListener: BindingCommand<Void> = BindingCommand(BindingAction {
        page = 1
        getNoticeList()
    })

    fun getNoticeList(){
        model.noticeDetail(page, rows)
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object :ApiSubscriberHelper<BaseBean<NoticeBean>>(){
                override fun onResult(t: BaseBean<NoticeBean>) {
                    if (t.success == true){
                        page++
                        uc.getNoticeDetail.postValue(t?.data?.list?.rows)
                    }else{
                        uc.getNoticeDetail.postValue(null)
                    }
                }

                override fun onFailed(msg: String?) {
                    uc.getNoticeDetail.postValue(null)
                }
            })
    }

}