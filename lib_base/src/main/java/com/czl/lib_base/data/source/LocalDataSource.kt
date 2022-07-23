package com.czl.lib_base.data.source

import com.czl.lib_base.data.bean.UserInfoBean
import java.io.Serializable

/**
 * @author Alwyn
 * @Date 2020/7/20
 * @Description
 */
interface LocalDataSource {
    //所有部门
//    fun saveAllDepartmentsDetal(workDepartmentsList: List<UserInfoBean.Deptl>)
//    fun getAllDepartmentsDetal():List<UserInfoBean.Deptl>?

    //岗位级别
//    fun saveJobLevelDetail(jobLevelList:List<UserInfoBean.Post>)
//    fun getJobLevelDetail():List<UserInfoBean.Post>?

    //民族
//    fun saveNationalityDetail(nationalityList:List<UserInfoBean.Nation>)
//    fun getNationalityDetail():List<UserInfoBean.Nation>

    //技能级别
//    fun saveSkillLevelDetail(skillLevelList:List<UserInfoBean.SkillLevel>)
//    fun getSkillLevelDetail():List<UserInfoBean.SkillLevel>?

    //学历
//    fun saveEducationDetail(educationList:List<UserInfoBean.Edu>)
//    fun getEducationDetail():List<UserInfoBean.Edu>

    fun <T : Serializable> saveCacheListData(list: List<T>)
    fun <T : Serializable> getCacheListData(key:String): List<T>?

}