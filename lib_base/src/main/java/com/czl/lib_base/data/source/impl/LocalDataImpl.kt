package com.czl.lib_base.data.source.impl

import com.blankj.utilcode.util.CacheDiskUtils
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.*
import com.czl.lib_base.data.source.LocalDataSource
import java.io.Serializable

/**
 * @author Alwyn
 * @Date 2020/7/20
 * @Description
 */
class LocalDataImpl : LocalDataSource {
    override fun <T : Serializable> saveCacheListData(list: List<T>) {
        if (list.isNotEmpty()) {
            when (list[0]) {
                is UserInfoBean.Deptl -> {
                    CacheDiskUtils.getInstance().put(
                        AppConstants.CacheKey.CACHE_ALL_DEPART,
                        AllDepartmentsCache(list as List<UserInfoBean.Deptl>),
                        AppConstants.CacheKey.CACHE_SAVE_TIME_SECONDS
                    )
                }

                is UserInfoBean.Post -> {
                    CacheDiskUtils.getInstance().put(
                        AppConstants.CacheKey.CACHE_JOB_LEVEL,
                        JobLevelDetailCache(list as List<UserInfoBean.Post>),
                        AppConstants.CacheKey.CACHE_SAVE_TIME_SECONDS
                    )
                }

                is UserInfoBean.Nation -> {
                    CacheDiskUtils.getInstance().put(
                        AppConstants.CacheKey.CACHE_NATIONALITY,
                        NationalityDetailCache(list as List<UserInfoBean.Nation>),
                        AppConstants.CacheKey.CACHE_SAVE_TIME_SECONDS
                    )
                }

                is UserInfoBean.SkillLevel -> {
                    CacheDiskUtils.getInstance().put(
                        AppConstants.CacheKey.CACHE_SKILL_LEVEL,
                        SkillLevelDetailCache(list as List<UserInfoBean.SkillLevel>),
                        AppConstants.CacheKey.CACHE_SAVE_TIME_SECONDS
                    )
                }

                is UserInfoBean.Edu -> {
                    CacheDiskUtils.getInstance().put(
                        AppConstants.CacheKey.CACHE_EDUCATION,
                        EducationDetailCache(list as List<UserInfoBean.Edu>),
                        AppConstants.CacheKey.CACHE_SAVE_TIME_SECONDS
                    )
                }
            }
        }
    }

    override fun <T : Serializable> getCacheListData(key: String): List<T>? {
        return when(val serializable = CacheDiskUtils.getInstance().getSerializable(key)){
            is AllDepartmentsCache? ->{
                serializable?.workDepartmentsList as List<T>?
            }
            is JobLevelDetailCache? ->{
                serializable?.jobLevelDetailCache as List<T>?
            }
            is NationalityDetailCache? ->{
                serializable?.nationalityDetailCache as List<T>?
            }
            is SkillLevelDetailCache? ->{
                serializable?.skillLevelDetailCache as List<T>?
            }
            is EducationDetailCache? ->{
                serializable?.educationDetailCache as List<T>?
            }
            else -> emptyList()
        }
    }

}