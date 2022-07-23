package com.czl.lib_base.data

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.base.BaseModel
import com.czl.lib_base.data.bean.*
import com.czl.lib_base.data.source.HttpDataSource
import com.czl.lib_base.data.source.LocalDataSource
import io.reactivex.Observable
import java.io.Serializable

/**
 * @author Alwyn
 * @Date 2020/7/20
 * @Description 数据中心（本地+在线） 外部通过Koin依赖注入调用
 * 对于缓存或者在线数据的增删查改统一通过该数据仓库调用
 */
class DataRepository constructor(
    private val mLocalDataSource: LocalDataSource,
    private val mHttpDataSource: HttpDataSource
) : BaseModel(), LocalDataSource, HttpDataSource {

    override fun userLogin(
        account: String,
        pwd: String,
        endpoint: String
    ): Observable<BaseBean<LoginUserBean>> {
        return mHttpDataSource.userLogin(account, pwd, endpoint)
    }

    override fun mineUserInfo(): Observable<BaseBean<MineUserInfoBean>> {
        return mHttpDataSource.mineUserInfo()
    }

    override fun homeDetail(): Observable<BaseBean<HomeDetailBean>> {
        return mHttpDataSource.homeDetail()
    }

    override fun courseTabDetail(): Observable<BaseBean<List<CourseTabItemBeanItem>>> {
        return mHttpDataSource.courseTabDetail()
    }

    override fun courseListDetail(
        page: Int,
        rows: Int,
        title: String
    ): Observable<BaseBean<CourseListBean>> {
        return mHttpDataSource.courseListDetail(page, rows, title)
    }

    override fun noticeDetail(page: Int, rows: Int): Observable<BaseBean<NoticeBean>> {
        return mHttpDataSource.noticeDetail(page, rows)
    }

    override fun queryUserInfo(): Observable<BaseBean<UserInfoBean>> {
        return mHttpDataSource.queryUserInfo()
    }

    override fun saveUserAvatar(portait: String): Observable<BaseBean<String>> {
        return mHttpDataSource.saveUserAvatar(portait)
    }

    override fun updateUserInfo(map: Map<String, String>): Observable<BaseBean<String>> {
        return mHttpDataSource.updateUserInfo(map)
    }

    override fun getRegisterCode(phone: String): Observable<BaseBean<String>> {
        return mHttpDataSource.getRegisterCode(phone)
    }

    override fun getRegisterAccount(map: Map<String, String>): Observable<BaseBean<String>> {
        return mHttpDataSource.getRegisterAccount(map)
    }

    override fun getallDept(page: Int, rows: Int,name: String): Observable<List<DeptBeanItem>> {
        return mHttpDataSource.getallDept(page, rows, name)
    }

    override fun getMyCourses(
        page: Int,
        rows: Int,
        enrollType: String,
        top: String
    ): Observable<BaseBean<MyCoursesBean>> {
        return mHttpDataSource.getMyCourses(page, rows, enrollType, top)
    }

    override fun getKpsTree(level: String, akpid: String): Observable<BaseBean<QuestionDataBean>> {
        return mHttpDataSource.getKpsTree(level, akpid)
    }

    override fun getSpecialPracticeNum(knowledgeId: String): Observable<BaseBean<SpecialExercisesBean>> {
        return mHttpDataSource.getSpecialPracticeNum(knowledgeId)
    }

    override fun getPracticeDetail(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return mHttpDataSource.getPracticeDetail(map)
    }

    override fun getUnpracticedQuestions(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return mHttpDataSource.getUnpracticedQuestions(map)
    }

    override fun getSpecialExercises(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return mHttpDataSource.getSpecialExercises(map)
    }

    override fun addMyCollect(map: Map<String, String>): Observable<BaseBean<String>> {
        return mHttpDataSource.addMyCollect(map)
    }

    override fun unfavorite(map: Map<String, String>): Observable<BaseBean<String>> {
        return mHttpDataSource.unfavorite(map)
    }

    override fun getErrorPractice(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return mHttpDataSource.getErrorPractice(map)
    }

    override fun getCollectedPractice(map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>> {
        return mHttpDataSource.getCollectedPractice(map)
    }

    override fun getMyExamList(map: Map<String, String>): Observable<BaseBean<MyExamDetailBean>> {
        return mHttpDataSource.getMyExamList(map)
    }

    override fun <T : Serializable> saveCacheListData(list: List<T>) {
        return mLocalDataSource.saveCacheListData(list)
    }

    override fun <T : Serializable> getCacheListData(key: String): List<T>? {
        return mLocalDataSource.getCacheListData(key)
    }


}