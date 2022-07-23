package com.czl.lib_base.data.source.impl

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.api.ApiService
import com.czl.lib_base.data.bean.*
import com.czl.lib_base.data.source.HttpDataSource
import io.reactivex.Observable
import java.util.*
import kotlin.collections.HashMap

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
class HttpDataImpl(private val apiService: ApiService) : HttpDataSource {

    override fun userLogin(
        account: String,
        pwd: String,
        endpoint: String
    ): Observable<BaseBean<LoginUserBean>> {
        return apiService.pwdLogin(account, pwd, endpoint)
    }

    override fun mineUserInfo(): Observable<BaseBean<MineUserInfoBean>> {
        return apiService.getUserInfo()
    }

    override fun homeDetail(): Observable<BaseBean<HomeDetailBean>> {
        return apiService.getHomeDetail()
    }

    override fun courseTabDetail(): Observable<BaseBean<List<CourseTabItemBeanItem>>> {
        return apiService.getCourseTabDetail()
    }

    override fun courseListDetail(
        page: Int,
        rows: Int,
        title: String
    ): Observable<BaseBean<CourseListBean>> {
        return apiService.getCourseListDetail(page, rows, title)
    }

    override fun noticeDetail(page: Int, rows: Int): Observable<BaseBean<NoticeBean>> {
        return apiService.getNoticeDetail(page, rows)
    }

    override fun queryUserInfo(): Observable<BaseBean<UserInfoBean>> {
        return apiService.queryUserInfo()
    }

    override fun saveUserAvatar(portait: String): Observable<BaseBean<String>> {
        return apiService.saveUserAvatar(portait)
    }

    override fun updateUserInfo(map: Map<String, String>): Observable<BaseBean<String>> {
        return apiService.updateUserInfo(map)
    }

    override fun getRegisterCode(phone: String): Observable<BaseBean<String>> {
        return apiService.getRegisterCode(phone)
    }

    override fun getRegisterAccount(map: Map<String, String>): Observable<BaseBean<String>> {
        return apiService.getRegisterAccount(map)
    }

    override fun getallDept(page: Int, rows: Int, name: String): Observable<List<DeptBeanItem>> {
        return apiService.getallDept(page, rows, name)
    }

    override fun getMyCourses(
        page: Int,
        rows: Int,
        enrollType: String,
        top: String
    ): Observable<BaseBean<MyCoursesBean>> {
        return apiService.getMyCourses(page, rows, enrollType, top)
    }

    override fun getKpsTree(level: String, akpid: String): Observable<BaseBean<QuestionDataBean>> {
        return apiService.getKpsTree(level, akpid)
    }

    override fun getSpecialPracticeNum(knowledgeId: String): Observable<BaseBean<SpecialExercisesBean>> {
        return apiService.getSpecialPracticeNum(knowledgeId)
    }

    override fun getPracticeDetail(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return apiService.getPracticeDetail(map)
    }

    override fun getUnpracticedQuestions(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return apiService.getUnpracticedQuestions(map)
    }

    override fun getSpecialExercises(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return apiService.getSpecialExercises(map)
    }

    override fun addMyCollect(map: Map<String, String>): Observable<BaseBean<String>> {
        return apiService.addMyCollect(map)
    }

    override fun unfavorite(map: Map<String, String>): Observable<BaseBean<String>> {
        return apiService.unfavorite(map)
    }

    override fun getErrorPractice(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return apiService.getErrorPractice(map)
    }

    override fun getCollectedPractice(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return apiService.getCollectedPractice(map)
    }

    override fun getMyExamList(map: Map<String, String>): Observable<BaseBean<MyExamDetailBean>> {
        return apiService.getMyExamList(map)
    }

}