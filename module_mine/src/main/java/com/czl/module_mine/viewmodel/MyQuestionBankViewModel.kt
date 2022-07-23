package com.czl.module_mine.viewmodel

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.QuestionDataBean
import com.czl.lib_base.data.bean.SpecialExercisesBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.SpHelper
import com.czl.lib_base.util.ToastHelper

class MyQuestionBankViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var akpid = ObservableField("")//知识点id
    var knowledgeName = ObservableField("")//知识点名称
    var level = ObservableField("1")//知识点层级 1子级 0主级
    var wrongQuesNum = ObservableInt(0)//错题数
    var collectNum = ObservableInt(0)//收藏数
    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val onQuestionDataEvent: SingleLiveEvent<QuestionDataBean> = SingleLiveEvent()
        val onSpecialExercisesEvent: SingleLiveEvent<SpecialExercisesBean> = SingleLiveEvent()
        val onShowSpecialExercisesEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    }

    val onRefreshListener: BindingCommand<Void> = BindingCommand(BindingAction {

    })

    val onLoadMoreListener: BindingCommand<Void> = BindingCommand(BindingAction {

    })

    val onSpecialExercisesCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.onShowSpecialExercisesEvent.call()
    })

    val onGoAllKnowledgeCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        startContainerActivity(AppConstants.Router.Mine.F_ALL_KNOWLEDGE)
    })

    val onDailyPracticeCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        if (akpid.get()!!.isEmpty()) {
            ToastHelper.showErrorToast("请选择知识点！")
            return@BindingAction
        }
        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
            putString(AppConstants.BundleKey.KNOWLEDGE_ID, akpid.get()!!)
            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE, "每日一练")
            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID, 0)
            putString(AppConstants.BundleKey.KNOWLEDGE_NAME, knowledgeName.get()!!)
        })
    })

    val onBackTopicCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        if (akpid.get()!!.isEmpty()) {
            ToastHelper.showErrorToast("请选择知识点！")
            return@BindingAction
        }
        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
            putString(AppConstants.BundleKey.KNOWLEDGE_ID, akpid.get()!!)
            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE, "背题模式")
            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID, 1)
            putString(AppConstants.BundleKey.KNOWLEDGE_NAME, knowledgeName.get()!!)
        })
    })

    val onUnpracticedQuestionsCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        if (akpid.get()!!.isEmpty()) {
            ToastHelper.showErrorToast("请选择知识点！")
            return@BindingAction
        }
        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
            putString(AppConstants.BundleKey.KNOWLEDGE_ID, akpid.get()!!)
            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE, "未练习题")
            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID, 2)
            putString(AppConstants.BundleKey.KNOWLEDGE_NAME, knowledgeName.get()!!)
        })
    })

    val onErrorPracticeCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        if (akpid.get()!!.isEmpty()) {
            ToastHelper.showErrorToast("请选择知识点！")
            return@BindingAction
        }
        if (wrongQuesNum.get() == 0) {
            ToastHelper.showErrorToast("当前错题本没有数据！")
            return@BindingAction
        }
        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
            putString(AppConstants.BundleKey.KNOWLEDGE_ID, akpid.get()!!)
            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE, "错题本")
            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID, 4)
            putString(AppConstants.BundleKey.KNOWLEDGE_NAME, knowledgeName.get()!!)
        })
    })

    val onCollectedPracticeCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        if (akpid.get()!!.isEmpty()) {
            ToastHelper.showErrorToast("请选择知识点！")
            return@BindingAction
        }
        if (collectNum.get() == 0) {
            ToastHelper.showErrorToast("当前收藏夹没有数据！")
            return@BindingAction
        }
        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
            putString(AppConstants.BundleKey.KNOWLEDGE_ID, akpid.get()!!)
            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE, "收藏夹")
            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID, 5)
            putString(AppConstants.BundleKey.KNOWLEDGE_NAME, knowledgeName.get()!!)
        })
    })

    val onMyAbilityAnalysisCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        if (akpid.get()!!.isEmpty()) {
            ToastHelper.showErrorToast("请选择知识点！")
            return@BindingAction
        }

        var url =
            "${AppConstants.Url.BASE_URL}xpzx/app/platStudent/kpStat?kpid=${akpid.get()}&appUserId=${
                SpHelper.decodeString(AppConstants.SpKey.USER_ID)
            }"

        startContainerActivity(AppConstants.Router.Web.F_WEB_NA, Bundle().apply {
            putString(AppConstants.BundleKey.WEB_NAME, "我的能力分析")
            putString(AppConstants.BundleKey.WEB_URL, url)
            putInt(AppConstants.BundleKey.WEB_TYPE, 1)
        })

    })

    fun getKpsTreeData() {
        model.getKpsTree(level.get()!!, akpid.get()!!)
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<QuestionDataBean>>() {
                override fun onResult(t: BaseBean<QuestionDataBean>) {
                    if (t.success == true) {
                        if (t.data?.kpid?.isNotEmpty() == true) {
                            akpid.set(t.data?.kpid)
                            uc.onQuestionDataEvent.postValue(t.data)
                            getSpecialPracticeNum()
                        }
                    } else {
                        uc.onQuestionDataEvent.postValue(null)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                    uc.onQuestionDataEvent.postValue(null)
                }
            })
    }

    fun getSpecialPracticeNum() {
        model.getSpecialPracticeNum(akpid.get()!!)
            .compose(RxThreadHelper.rxSchedulerHelper(this))
            .subscribe(object : ApiSubscriberHelper<BaseBean<SpecialExercisesBean>>() {
                override fun onResult(t: BaseBean<SpecialExercisesBean>) {
                    if (t.success == true) {
                        uc.onSpecialExercisesEvent.postValue(t.data)
                    }
                }

                override fun onFailed(msg: String?) {
                    ToastHelper.showErrorToast(msg)
                }
            })
    }

}