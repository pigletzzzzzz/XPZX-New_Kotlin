package com.czl.lib_base.event

import com.jeremyliao.liveeventbus.core.LiveEvent

/**
 * @author Alwyn
 * @Date 2020/10/15
 * @Description 页面通信事件
 */
data class RegisterSuccessEvent(val account: String?, val pwd: String?) : LiveEvent
data class LoginSuccessEvent(val code: Int) : LiveEvent
data class RefreshWebListEvent(val code: Int) : LiveEvent
data class TokenExpiredEvent(val msg: String?) : LiveEvent
data class LogoutEvent(val code: Int) : LiveEvent
data class DeptSelectDataEvent(val name: String, val id: String) : LiveEvent
data class QuestionInfoEvent(val info: String?) : LiveEvent
data class KnowledgeSelectDataEvent(val name: String, val id: String) : LiveEvent
data class PracticeIdPositionEvent(val id: String, val pos: Int) : LiveEvent
