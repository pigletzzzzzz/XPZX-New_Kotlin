package com.czl.lib_base.data.api

import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.data.bean.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
interface ApiService {

    /**
     * 登录
     */
    @POST("app/doLogin")
    @FormUrlEncoded
    fun pwdLogin(
        @Field("loginCode") username: String,
        @Field("loginPass") password: String,
        @Field("endpoint") endpoint: String
    ): Observable<BaseBean<LoginUserBean>>

    /**
     * 登录个人信息
     */
    @POST("xpzx/app/platStudent/myCenter")
    fun getUserInfo(): Observable<BaseBean<MineUserInfoBean>>

    /**
     * 首页数据
     */
    @POST("xpzx/V2/app/V3/index")
    fun getHomeDetail(): Observable<BaseBean<HomeDetailBean>>

    /**
     * 课程Tab数据
     */
    @POST("xpzx/app/course/courseSortList?pid=erji&children=1")
    fun getCourseTabDetail(): Observable<BaseBean<List<CourseTabItemBeanItem>>>

    /**
     * 课程列表
     */
    @POST("xpzx/app/course/newCourseList")
    @FormUrlEncoded
    fun getCourseListDetail(
        @Field("page") page: Int,
        @Field("rows") rows: Int,
        @Field("title") title: String
    ): Observable<BaseBean<CourseListBean>>

    /**
     * 公告列表
     */
    @POST("xpzx/V2/app/inform")
    @FormUrlEncoded
    fun getNoticeDetail(
        @Field("page") page: Int,
        @Field("rows") rows: Int
    ): Observable<BaseBean<NoticeBean>>

    /**
     * 用户信息
     */
    @POST("app/platStudent/queryStudentInfo")
    fun queryUserInfo(): Observable<BaseBean<UserInfoBean>>

    /**
     * 保存图片地址
     */
    @POST("app/platStudent/updateUserInfo")
    @FormUrlEncoded
    fun saveUserAvatar(@Field("portait") portait: String): Observable<BaseBean<String>>

    /**
     * 更新个人数据
     */
    @POST("app/platStudent/updateUserInfo")
    @FormUrlEncoded
    fun updateUserInfo(@FieldMap map: Map<String, String>): Observable<BaseBean<String>>

    /**
     * 获取注册手机验证码
     */
    @POST("xpzx/front/register/sendvc")
    @FormUrlEncoded
    fun getRegisterCode(@Field("phone") phone: String): Observable<BaseBean<String>>

    /**
     * 注册账号
     */
    @POST("xpzx/front/register/doRegister")
    @FormUrlEncoded
    fun getRegisterAccount(@FieldMap map: Map<String, String>): Observable<BaseBean<String>>

    /**
     * 部门列表
     */
    @POST("xpzx/front/allDept")
    @FormUrlEncoded
    fun getallDept(
        @Field("page") page: Int,
        @Field("rows") rows: Int,
        @Field("name") name: String
    ): Observable<List<DeptBeanItem>>

    /**
     * 我的课程
     */
    @POST("xpzx/app/course/newCourseList")
    @FormUrlEncoded
    fun getMyCourses(
        @Field("page") page: Int,
        @Field("rows") rows: Int,
        @Field("enrollType") enrollType: String,
        @Field("top") top: String
    ): Observable<BaseBean<MyCoursesBean>>

    /**
     * 我的题库数据详情
     */
    @POST("xpzx/front/exercise/kpsTreeV3")
    @FormUrlEncoded
    fun getKpsTree(
        @Field("level") level: String,
        @Field("akpid") akpid: String
    ): Observable<BaseBean<QuestionDataBean>>

    /**
     * 我的题库练习数据
     */
    @POST("xpzx/app/index/specialPracticeNum")
    @FormUrlEncoded
    fun getSpecialPracticeNum(
        @Field("knowledgeId") knowledgeId: String
    ): Observable<BaseBean<SpecialExercisesBean>>

    /**
     * 我的题库每日一练、背题模式
     */
    @POST("xpzx/app/index/randomPractice")
    @FormUrlEncoded
    fun getPracticeDetail(@FieldMap map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>>

    /**
     * 我的题库未练习题
     */
    @POST("xpzx/app/index/needPractice")
    @FormUrlEncoded
    fun getUnpracticedQuestions(@FieldMap map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>>

    /**
     * 我的题库专项练习
     */
    @POST("xpzx/app/index/specialPractice")
    @FormUrlEncoded
    fun getSpecialExercises(@FieldMap map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>>

    /**
     * 我的题库题目加入收藏
     */
    @POST("xpzx/app/index/addMyCollect")
    @FormUrlEncoded
    fun addMyCollect(@FieldMap map: Map<String, String>): Observable<BaseBean<String>>

    /**
     * 我的题库题目取消收藏
     */
    @POST("xpzx/app/index/cancelCollect")
    @FormUrlEncoded
    fun unfavorite(@FieldMap map: Map<String, String>): Observable<BaseBean<String>>

    /**
     * 我的题库题错题本
     */
    @POST("xpzx/app/index/errorPractice")
    @FormUrlEncoded
    fun getErrorPractice(@FieldMap map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>>

    /**
     * 我的题库题收藏夹
     */
    @POST("xpzx/app/index/collectedPractice")
    @FormUrlEncoded
    fun getCollectedPractice(@FieldMap map: Map<String, String>): Observable<BaseBean<SpecialDetailBean>>

    /**
     * 我的考试 status 空为全部考试 1为进行中 2为未开始  3为已结束
     */
    @POST("xpzx/app/exam/examList")
    @FormUrlEncoded
    fun getMyExamList(@FieldMap map: Map<String, String>): Observable<BaseBean<MyExamDetailBean>>

}