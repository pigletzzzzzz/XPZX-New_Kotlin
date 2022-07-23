package com.czl.lib_base.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author Alwyn
 * @Date 2020/10/16
 * @Description 事件分发中心管理
 * 可通过Kotlin的命名可选参数特性 一个方法即可判断 发送/接收
 * sticky : post/observeSticky
 */
object LiveBusCenter {

    fun postTokenExpiredEvent(value: String?) {
        LiveEventBus.get(TokenExpiredEvent::class.java).post(TokenExpiredEvent(value))
    }

    fun observeTokenExpiredEvent(owner: LifecycleOwner, observer: Observer<TokenExpiredEvent>) {
        LiveEventBus.get(TokenExpiredEvent::class.java).observe(owner, observer)
    }

    fun postRegisterSuccessEvent(account: String?, pwd: String?) {
        LiveEventBus.get(RegisterSuccessEvent::class.java).post(RegisterSuccessEvent(account, pwd))
    }

    fun observeRegisterSuccessEvent(
        owner: LifecycleOwner,
        func: (t: RegisterSuccessEvent) -> Unit
    ) {
        LiveEventBus.get(RegisterSuccessEvent::class.java).observe(owner, Observer(func))
    }

    fun postLoginSuccessEvent() {
        LiveEventBus.get(LoginSuccessEvent::class.java).post(LoginSuccessEvent(0))
    }

    fun postRefreshWebListEvent() {
        LiveEventBus.get(RefreshWebListEvent::class.java).post(RefreshWebListEvent(0))
    }

    fun postDeptSelectSuccessEvent(name: String, id: String){
        LiveEventBus.get(DeptSelectDataEvent::class.java).post(DeptSelectDataEvent(name, id))
    }

    fun observeDeptSelectEvent(owner: LifecycleOwner,observer: Observer<DeptSelectDataEvent>){
        LiveEventBus.get(DeptSelectDataEvent::class.java).observe(owner, observer)
    }

    fun postQuestionInfoEvent(info: String){
        LiveEventBus.get(QuestionInfoEvent::class.java).post(QuestionInfoEvent(info))
    }

    fun observeQuestionInfoEvent(owner: LifecycleOwner,observer: Observer<QuestionInfoEvent>){
        LiveEventBus.get(QuestionInfoEvent::class.java).observe(owner, observer)
    }

    fun postKnowledgeSelectSuccessEvent(name: String, id: String){
        LiveEventBus.get(KnowledgeSelectDataEvent::class.java).post(KnowledgeSelectDataEvent(name, id))
    }

    fun observeKnowledgeSelectEvent(owner: LifecycleOwner,observer: Observer<KnowledgeSelectDataEvent>){
        LiveEventBus.get(KnowledgeSelectDataEvent::class.java).observe(owner, observer)
    }

    fun postPracticeIdPositionEvent(id: String, pos: Int){
        LiveEventBus.get(PracticeIdPositionEvent::class.java).post(PracticeIdPositionEvent(id,pos))
    }

    fun observePracticeIdPositionEvent(owner: LifecycleOwner,observer: Observer<PracticeIdPositionEvent>){
        LiveEventBus.get(PracticeIdPositionEvent::class.java).observe(owner, observer)
    }

}