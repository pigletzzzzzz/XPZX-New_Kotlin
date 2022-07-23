package com.czl.module_mine.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.QuestionDataBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.ToastHelper

class AllKnowledgeViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val onQuestionDataEvent: SingleLiveEvent<QuestionDataBean> = SingleLiveEvent()
    }

    fun getKpsTreeData(){
        model.getKpsTree("","")
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<QuestionDataBean>>(){
                override fun onResult(t: BaseBean<QuestionDataBean>) {
                    if (t.success == true){
                        if (t.data?.kpid?.isNotEmpty() == true){
                            uc.onQuestionDataEvent.postValue(t.data)
                        }
                    }else{
                        uc.onQuestionDataEvent.postValue(null)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                    uc.onQuestionDataEvent.postValue(null)
                }
            })
    }
}