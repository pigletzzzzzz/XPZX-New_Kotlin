package com.czl.module_mine.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.MyExamDetailBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper

/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 16:01

 * 描述:

 */
class MyExamListViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var page = ObservableField("1")
    var rows = ObservableField("10")
    var status = ObservableField("")
    val uc = UiChangeEvent()

    class UiChangeEvent {
        var onMyExamDetailEvent: SingleLiveEvent<MyExamDetailBean> = SingleLiveEvent()
    }

    fun getMyExam() {
        val map = mapOf<String, String>(
            "page" to page.get()!!,
            "rows" to rows.get()!!,
            "status" to status.get()!!
        )
        model.getMyExamList(map)
            .doOnSubscribe { showLoading() }
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<MyExamDetailBean>>() {
                override fun onResult(t: BaseBean<MyExamDetailBean>) {
                    if (t.success == true) {
                        uc.onMyExamDetailEvent.postValue(t.data)
                    } else {
                        uc.onMyExamDetailEvent.postValue(null)
                    }
                }

                override fun onFailed(msg: String?) {
                    uc.onMyExamDetailEvent.postValue(null)
                }
            })
    }

}