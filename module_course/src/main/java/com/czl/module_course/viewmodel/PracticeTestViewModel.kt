package com.czl.module_course.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.test.espresso.remote.EspressoRemoteMessage
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository
import com.czl.lib_base.data.bean.SpecialDetailBean
import com.czl.lib_base.extension.ApiSubscriberHelper
import com.czl.lib_base.util.RxThreadHelper
import com.czl.lib_base.util.ToastHelper
import com.czl.module_course.R

class PracticeTestViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var knowledgeId = ObservableField("")//
    var questid = ObservableField("")//当前题目id
    var endpoint = ObservableField("android")//
    var questionId = ObservableField("")//下一题id
    var correct = ObservableField("")//
    var myanswer = ObservableField("")//我的答案
    var courseId = ObservableField("")//
    var questype = ObservableField("")//专项练习题型 3多选 1单选 2判断 6主观
    var exerid = ObservableField("")//当前题目exerid
    var kind = ObservableInt(0)//题目类型
    var isCorrect = ObservableInt(0)//当前题目收藏状态
    var knowledgeIdType = ObservableInt(0)//练习类别id  0为每日一练 1为背题模式 2为未练习题 3专项练习 4错题本 5收藏夹
    var map:Map<String,String> = mapOf()

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val onPracticeTestDetailEvent: SingleLiveEvent<SpecialDetailBean> = SingleLiveEvent()
        val onPreviousQuestionEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val onAnswerSheetEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val onTopicAnalysisEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val onNextQuestionEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val onMultipleChoiceToConfirmEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    }

    val onPreviousQuestionCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.onPreviousQuestionEvent.call()
    })

    val onAnswerSheetCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.onAnswerSheetEvent.call()
    })

    val onTopicAnalysisCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.onTopicAnalysisEvent.call()
    })

    val onNextQuestionCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.onNextQuestionEvent.call()
    })

    val onMultipleChoiceToConfirmCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.onMultipleChoiceToConfirmEvent.call()
    })

    override fun setToolbarRightClick() {
        setCollectType()
    }

    fun getPracticeTestDetail() {
        // 0为每日一练 1为背题模式 2为未练习题 3专项练习 4错题本 5收藏夹
        when(knowledgeIdType.get()){
            0,1 ->{
                map = mapOf<String, String>(
                    "knowledgeId" to knowledgeId.get()!!,
                    "questid" to questid.get()!!,
                    "endpoint" to endpoint.get()!!,
                    "questionId" to questionId.get()!!,
                    "correct" to correct.get()!!,
                    "myanswer" to myanswer.get()!!,
                    "courseId" to courseId.get()!!,
                    "exerid" to exerid.get()!!
                )
                model.getPracticeDetail(map)
                    .compose(RxThreadHelper.rxSchedulerHelper(this))
                    .subscribe(object : ApiSubscriberHelper<BaseBean<SpecialDetailBean>>() {
                        override fun onResult(t: BaseBean<SpecialDetailBean>) {
                            if (t.success == true) {
                                uc.onPracticeTestDetailEvent.postValue(t.data)
                            } else {
                                ToastHelper.showErrorToast(t.msg)
                                finish()
                            }
                        }

                        override fun onFailed(msg: String?) {
                            ToastHelper.showErrorToast(msg)
                            finish()
                        }
                    })
            }
            2 -> {
                map = mapOf<String, String>(
                    "knowledgeId" to knowledgeId.get()!!,
                    "questid" to questid.get()!!,
                    "endpoint" to endpoint.get()!!,
                    "questionId" to questionId.get()!!,
                    "correct" to correct.get()!!,
                    "myanswer" to myanswer.get()!!,
                    "courseId" to courseId.get()!!,
                    "exerid" to exerid.get()!!
                )
                model.getUnpracticedQuestions(map)
                    .compose(RxThreadHelper.rxSchedulerHelper(this))
                    .subscribe(object : ApiSubscriberHelper<BaseBean<SpecialDetailBean>>() {
                        override fun onResult(t: BaseBean<SpecialDetailBean>) {
                            if (t.success == true) {
                                uc.onPracticeTestDetailEvent.postValue(t.data)
                            } else {
                                ToastHelper.showErrorToast(t.msg)
                                finish()
                            }
                        }

                        override fun onFailed(msg: String?) {
                            ToastHelper.showErrorToast(msg)
                            finish()
                        }
                    })
            }

            3 -> {
                map = mapOf<String, String>(
                    "knowledgeId" to knowledgeId.get()!!,
                    "questid" to questid.get()!!,
                    "endpoint" to endpoint.get()!!,
                    "questionId" to questionId.get()!!,
                    "correct" to correct.get()!!,
                    "myanswer" to myanswer.get()!!,
                    "courseId" to courseId.get()!!,
                    "questype" to questype.get()!!,
                    "exerid" to exerid.get()!!
                )

                model.getSpecialExercises(map)
                    .compose(RxThreadHelper.rxSchedulerHelper(this))
                    .subscribe(object : ApiSubscriberHelper<BaseBean<SpecialDetailBean>>() {
                        override fun onResult(t: BaseBean<SpecialDetailBean>) {
                            if (t.success == true) {
                                uc.onPracticeTestDetailEvent.postValue(t.data)
                            } else {
                                ToastHelper.showErrorToast(t.msg)
                                finish()
                            }
                        }

                        override fun onFailed(msg: String?) {
                            ToastHelper.showErrorToast(msg)
                            finish()
                        }
                    })
            }

            4 -> {
                map = mapOf<String, String>(
                    "knowledgeId" to knowledgeId.get()!!,
                    "questid" to questid.get()!!,
                    "endpoint" to endpoint.get()!!,
                    "questionId" to questionId.get()!!,
                    "correct" to correct.get()!!,
                    "myanswer" to myanswer.get()!!,
                    "courseId" to courseId.get()!!,
                    "exerid" to exerid.get()!!
                )
                model.getErrorPractice(map)
                    .compose(RxThreadHelper.rxSchedulerHelper(this))
                    .subscribe(object : ApiSubscriberHelper<BaseBean<SpecialDetailBean>>() {
                        override fun onResult(t: BaseBean<SpecialDetailBean>) {
                            if (t.success == true) {
                                uc.onPracticeTestDetailEvent.postValue(t.data)
                            } else {
                                ToastHelper.showErrorToast(t.msg)
                                finish()
                            }
                        }

                        override fun onFailed(msg: String?) {
                            ToastHelper.showErrorToast(msg)
                            finish()
                        }
                    })
            }

            5 -> {
                map = mapOf<String, String>(
                    "knowledgeId" to knowledgeId.get()!!,
                    "questid" to questid.get()!!,
                    "endpoint" to endpoint.get()!!,
                    "questionId" to questionId.get()!!,
                    "correct" to correct.get()!!,
                    "myanswer" to myanswer.get()!!,
                    "courseId" to courseId.get()!!,
                    "exerid" to exerid.get()!!
                )
                model.getCollectedPractice(map)
                    .compose(RxThreadHelper.rxSchedulerHelper(this))
                    .subscribe(object : ApiSubscriberHelper<BaseBean<SpecialDetailBean>>() {
                        override fun onResult(t: BaseBean<SpecialDetailBean>) {
                            if (t.success == true) {
                                uc.onPracticeTestDetailEvent.postValue(t.data)
                            } else {
                                ToastHelper.showErrorToast(t.msg)
                                finish()
                            }
                        }

                        override fun onFailed(msg: String?) {
                            ToastHelper.showErrorToast(msg)
                            finish()
                        }
                    })
            }
        }
    }

    fun setCollectType(){
        map = mapOf<String, String>(
            "kpid" to knowledgeId.get()!!,
            "refid" to questid.get()!!,
            "reftype" to "3"
        )
        if (isCorrect.get() == 0){
            model.addMyCollect(map)
                .compose(RxThreadHelper.rxSchedulerHelper(this))
                .subscribe(object :ApiSubscriberHelper<BaseBean<String>>(){
                    override fun onResult(t: BaseBean<String>) {
                        if (t.success == true){
                            ivToolbarIconRes.set(R.mipmap.icon_tk_collect_y)
                            isCorrect.set(1)
                            ToastHelper.showSuccessToast(t.msg)
                        }else{
                            ToastHelper.showErrorToast(t.msg)
                        }
                    }

                    override fun onFailed(msg: String?) {
                        ToastHelper.showErrorToast(msg)
                    }
                })
        }else{
            model.unfavorite(map)
                .compose(RxThreadHelper.rxSchedulerHelper(this))
                .subscribe(object :ApiSubscriberHelper<BaseBean<String>>(){
                    override fun onResult(t: BaseBean<String>) {
                        if (t.success == true){
                            ivToolbarIconRes.set(R.mipmap.icon_tk_collect_n)
                            isCorrect.set(0)
                            ToastHelper.showSuccessToast(t.msg)
                        }else{
                            ToastHelper.showErrorToast(t.msg)
                        }
                    }

                    override fun onFailed(msg: String?) {
                        ToastHelper.showErrorToast(msg)
                    }
                })
        }

    }
}