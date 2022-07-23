package com.czl.module_mine.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.UserInfoBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.ToastHelper

class UserInfoViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var userName = ObservableField("")//用户姓名
    var sex = ObservableField("")//性别 男=1  女=0
    var nation = ObservableField("")//民族code
    var deptid = ObservableField("")//单位id
    var skillLevel = ObservableField("")//技能级别code
    var edu = ObservableField("")//学历code
    var post = ObservableField("")//岗位code
    var phone = ObservableField("")//手机号码
    var email = ObservableField("")//邮箱
    var portait = ObservableField("")//头像地址
    var loginCode = ObservableField("")//
    var birthday = ObservableField("")//生日
    var userno = ObservableField("")//
    var idcode = ObservableField("")//
    val uc = UiChangeEvent()

    class UiChangeEvent {
        val getUserInfoData: SingleLiveEvent<UserInfoBean> = SingleLiveEvent()
        val changeAvatarEvent = SingleLiveEvent<Void>()
        val changeSexEvent = SingleLiveEvent<Void>()
        val changeBirthdayEvent = SingleLiveEvent<Void>()
        val changeNationEvent = SingleLiveEvent<Void>()
        val changeEducationEvent = SingleLiveEvent<Void>()
        val changeSkillLevelEvent = SingleLiveEvent<Void>()
        val changePostEvent = SingleLiveEvent<Void>()
        val changeEmployerEvent = SingleLiveEvent<Void>()
        val submitInfoEvent = SingleLiveEvent<Void>()
        val uploadAvatarEvent = SingleLiveEvent<String>()
    }

    val changeAvatarCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.changeAvatarEvent.call()
    })
    val changeSexCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.changeSexEvent.call()
    })
    val changeBirthdayCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.changeBirthdayEvent.call()
    })
    val changeNationCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.changeNationEvent.call()
    })
    val changeEducationCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.changeEducationEvent.call()
    })
    val changeSkillLevelCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.changeSkillLevelEvent.call()
    })
    val changePostCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.changePostEvent.call()
    })
    val changeEmployerCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.changeEmployerEvent.call()
    })

    val submitInfoCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.submitInfoEvent.call()
    })

    fun saveUserAvatar() {
        model.saveUserAvatar(portait.get()!!).compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<String>>() {
                override fun onResult(t: BaseBean<String>) {
                    if (t.success == true) {
                        ToastHelper.showSuccessToast(t.msg)
                        uc.uploadAvatarEvent.postValue(portait.get())
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                }
            })
    }

    fun updateUserInfo() {
        val map = mapOf<String, String>(
            "userName" to userName.get()!!,
            "sex" to sex.get()!!,
            "nation" to nation.get()!!,
            "deptid" to deptid.get()!!,
            "skillLevel" to skillLevel.get()!!,
            "edu" to edu.get()!!,
            "post" to post.get()!!,
            "phone" to phone.get()!!,
            "email" to email.get()!!,
            "portait" to portait.get()!!,
            "loginCode" to loginCode.get()!!,
            "birthday" to birthday.get()!!,
            "userno" to userno.get()!!,
            "idcode" to idcode.get()!!
        )
        Log.e("info----", map.toString())
        model.updateUserInfo(map).compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<String>>() {
                override fun onResult(t: BaseBean<String>) {
                    if (t.success == true) {
                        ToastHelper.showSuccessToast("修改成功！")
                        finish()
                    } else {
                        ToastHelper.showSuccessToast(t.msg)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showSuccessToast("修改失败！")
                }
            })
    }

    fun getUserInfoDetail() {
        model.queryUserInfo().compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<UserInfoBean>>() {
                override fun onResult(t: BaseBean<UserInfoBean>) {
                    if (t.success == true) {
                        uc.getUserInfoData.postValue(t.data)
                    } else {
                        ToastHelper.showErrorToast(t.msg)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                }
            })
    }

    fun getEdusCaChe(): List<UserInfoBean.Edu> {
        return model.getCacheListData(AppConstants.CacheKey.CACHE_EDUCATION) ?: emptyList()
    }

    fun getSkillLevelsCache(): List<UserInfoBean.SkillLevel> {
        return model.getCacheListData(AppConstants.CacheKey.CACHE_SKILL_LEVEL) ?: emptyList()
    }

    fun getNationsCache(): List<UserInfoBean.Nation> {
        return model.getCacheListData(AppConstants.CacheKey.CACHE_NATIONALITY) ?: emptyList()
    }

    fun getPostsCache(): List<UserInfoBean.Post> {
        return model.getCacheListData(AppConstants.CacheKey.CACHE_JOB_LEVEL) ?: emptyList()
    }

    fun getDeptlListCache(): List<UserInfoBean.Deptl> {
        return model.getCacheListData(AppConstants.CacheKey.CACHE_ALL_DEPART) ?: emptyList()
    }

}