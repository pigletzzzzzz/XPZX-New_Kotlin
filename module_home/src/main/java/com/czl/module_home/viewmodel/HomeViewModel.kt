package com.czl.module_home.viewmodel

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.HomeDetailBean
import com.czl.lib_base.data.bean.MineUserInfoBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.ToastHelper

class HomeViewModel(application: MyApplication,model: DataRepository):BaseViewModel<DataRepository>(application, model) {

    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getHomeDetail : SingleLiveEvent<HomeDetailBean> = SingleLiveEvent()
    }

    fun getHomeDetail(){
        model.homeDetail().compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<HomeDetailBean>>() {
                override fun onResult(t: BaseBean<HomeDetailBean>) {
                    if (t.success == true){
                        t.data.apply {
                            uc.getHomeDetail.postValue(this)
                        }
                    }
                }

                override fun onFailed(msg: String?) {
                    uc.getHomeDetail.postValue(null)
                    ToastHelper.showErrorToast("获取首页数据失败${msg}")
                }
            })
    }
}