package com.czl.lib_base.data.bean

import java.io.Serializable

/**
 * @author Alwyn
 * @Date 2021/1/29
 * @Description 进入App第一帧缓存数据 避免每次进入app重新请求网络耗时耗流量 过期时长在AppConst类中控制
 */

//所有部门
data class AllDepartmentsCache(val workDepartmentsList: List<UserInfoBean.Deptl>):Serializable

//岗位级别
data class JobLevelDetailCache(val jobLevelDetailCache: List<UserInfoBean.Post>):Serializable

//民族
data class NationalityDetailCache(val nationalityDetailCache: List<UserInfoBean.Nation>):Serializable

//技能级别
data class SkillLevelDetailCache(val skillLevelDetailCache: List<UserInfoBean.SkillLevel>):Serializable

//学历
data class EducationDetailCache(val educationDetailCache: List<UserInfoBean.Edu>):Serializable