package com.czl.lib_base.config

/**
 * @author Alwyn
 * @Date 2020/10/22
 * @Description 常量管理类
 */
interface AppConstants {

    object Url {
        //项目地址根路径
        const val BASE_URL: String = "https://xpzx.xjxpzx.com.cn/"

        //项目资源下载地址
        const val IMG_UPLOAD_URL: String = "https://xpzx.xjxpzx.com.cn:8098/"

        //项目头像上传地址
        const val IMG_UPLOAD_AVATAR_URL: String = "xpzx/platform/upload/uploadFile?folder=0&saveMode=false"
    }

    object HttpKey {
        const val NAME_BASE_URL = "BaseUrlName"
    }

    object AppKey {
        const val BUGLY_KEY = ""
    }
    object SpKey {
        const val LOGIN_NAME: String = "login_name"
        const val USER_ID: String = "user_id"
        const val USER_JSON_DATA: String = "user_json_data"
        const val SYS_UI_MODE: String = "sys_ui_mode"
        const val USER_UI_MODE: String = "user_ui_mode"
        const val APP_ACCOUNT: String = "app_account"
        const val APP_PASSWORD: String = "app_password"
        const val APP_TOOKEN: String = "app_user_token"
        const val APP_TOOKEN_NAME: String = "Token"
        const val IS_LOGIN: String = "user_is_login"
    }

    object BundleKey {
        const val WEB_URL = "web_url"//地址
        const val WEB_TYPE = "web_type"
        const val WEB_NAME = "web_name"
        const val WEB_ITEM_ID = "web_item_id"
        const val WEB_URL_COLLECT_FLAG = "web_url_collect_flag"
        const val EXAM_TYPE = "exam_type"//考试状态类别
        const val GO_EXAM_TYPE = "go_exam_type"//想要跳转到那个type页
        const val KNOWLEDGE_ID = "knowledge_id"//知识点ID
        const val KNOWLEDGE_NAME = "knowledge_name"//知识点名称
        const val KNOWLEDGE_TYPE = "knowledge_type"//知识点类别
        const val KNOWLEDGE_TYPE_ID = "knowledge_type_id"//知识点类别id
        const val SPECIAL_TYPE = "special_type"//专项练习类别
    }

    object CacheKey{
        // 缓存有效期时长1天 数据刷新会重新刷新时长
        const val CACHE_SAVE_TIME_SECONDS = 86400
        const val CACHE_ALL_DEPART = "cache_all_depart"//所有部门
        const val CACHE_JOB_LEVEL = "cache_job_level"//岗位级别
        const val CACHE_NATIONALITY = "cache_nationality"//民族
        const val CACHE_SKILL_LEVEL = "cache_skill_level"//技能级别
        const val CACHE_EDUCATION = "cache_education"//学历
    }

    /**
     * value规则： /(module后缀)/(所在类名)
     * 路由 A_ : Activity
     *     F_ : Fragment
     */
    interface Router {
        object Web {
            const val F_WEB = "/web/WebFragment"
            const val F_WEB_NA = "/web/NewAgentWebFragment"
            const val F_WEB_OR = "/web/OrdinaryWebFragment"
        }

        object Login {
            const val F_LOGIN = "/login/LoginFragment"
            const val F_REGISTER = "/login/RegisterFragment"
            const val F_DEPT = "/login/AllDeptFragment"
        }

        object Announcement {
            const val F_ANNOU = "/announcement/AnnounceFragment"
        }

        object Course{
            const val F_COURSE = "/course/CourseFragment"
            const val F_PRACTICE = "/course/PracticeTestFragment"
        }

        object Home{
            const val A_MAIN = "/home/MainActivity"
            const val F_HOME = "/home/HomeFragment"
        }

        object Mine{
            const val F_MINE = "/mine/MineFragment"
            const val F_SET = "/mine/SetingFragment"
            const val F_USER_INFO = "/mine/UserInfoFragment"
            const val F_MY_COURSES = "/mine/MyCoursesFragment"
            const val F_MY_QUESTION_BANK = "/mine/MyQuestionBankFragment"
            const val F_ALL_KNOWLEDGE = "/mine/AllKnowledgeFragment"
            const val F_MY_EXAM = "/mine/MyExamFragment"
            const val F_MY_EXAM_LIST = "/mine/MyExamListFragment"
        }
    }
}