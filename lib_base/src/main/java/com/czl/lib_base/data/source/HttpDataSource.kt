package com.czl.lib_base.data.source

import com.czl.lib_base.annotation.TodoOrder
import com.czl.lib_base.annotation.TodoPriority
import com.czl.lib_base.annotation.TodoType
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.Path

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
interface HttpDataSource {
    fun userLogin(
        account: String,
        pwd: String,
        endpoint: String
    ): Observable<BaseBean<LoginUserBean>>

    fun mineUserInfo(): Observable<BaseBean<MineUserInfoBean>>

    fun homeDetail(): Observable<BaseBean<HomeDetailBean>>

    fun courseTabDetail(): Observable<BaseBean<List<CourseTabItemBeanItem>>>

    fun courseListDetail(page: Int, rows: Int, title: String): Observable<BaseBean<CourseListBean>>

    fun noticeDetail(page: Int, rows: Int): Observable<BaseBean<NoticeBean>>

    fun queryUserInfo(): Observable<BaseBean<UserInfoBean>>

    fun saveUserAvatar(portait: String): Observable<BaseBean<String>>

    fun updateUserInfo(map: Map<String, String>): Observable<BaseBean<String>>

    fun getRegisterCode(phone:String):Observable<BaseBean<String>>

    fun getRegisterAccount(map: Map<String, String>):Observable<BaseBean<String>>

    fun getallDept(page: Int, rows: Int,name:String):Observable<List<DeptBeanItem>>

    fun getMyCourses(page: Int, rows: Int,enrollType:String,top:String):Observable<BaseBean<MyCoursesBean>>

    fun getKpsTree(level:String,akpid:String):Observable<BaseBean<QuestionDataBean>>

    fun getSpecialPracticeNum(knowledgeId: String):Observable<BaseBean<SpecialExercisesBean>>

    fun getPracticeDetail(map: Map<String, String>) :Observable<BaseBean<SpecialDetailBean>>

    fun getUnpracticedQuestions(map: Map<String, String>) :Observable<BaseBean<SpecialDetailBean>>

    fun getSpecialExercises(map: Map<String, String>) :Observable<BaseBean<SpecialDetailBean>>

    fun addMyCollect(map: Map<String, String>) :Observable<BaseBean<String>>

    fun unfavorite(map: Map<String, String>) :Observable<BaseBean<String>>

    fun getErrorPractice(map: Map<String, String>) :Observable<BaseBean<SpecialDetailBean>>

    fun getCollectedPractice(map: Map<String, String>) :Observable<BaseBean<SpecialDetailBean>>

    fun getMyExamList(map: Map<String, String>) :Observable<BaseBean<MyExamDetailBean>>

}