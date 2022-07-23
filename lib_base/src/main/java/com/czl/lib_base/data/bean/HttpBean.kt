package com.czl.lib_base.data.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Alwyn
 * @Date 2020/10/15
 * @Description
 */
data class UserBean(
    @SerializedName("admin")
    val admin: Boolean,
    @SerializedName("chapterTops")
    val chapterTops: List<Any>,
    @SerializedName("coinCount")
    val coinCount: Int,
    @SerializedName("collectIds")
    val collectIds: List<Any>,
    @SerializedName("email")
    val email: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("publicName")
    val publicName: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("username")
    val username: String
)

data class LoginUserBean(
    @SerializedName("account")
    val account: String,
    @SerializedName("authDeptId")
    val authDeptId: String,
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("deptId")
    val deptId: String,
    @SerializedName("deptName")
    val deptName: String,
    @SerializedName("depts")
    val depts: List<Dept>,
    @SerializedName("email")
    val email: String,
    @SerializedName("flag")
    val flag: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("idcode")
    val idcode: String,
    @SerializedName("isusing")
    val isusing: Boolean,
    @SerializedName("lastlogin")
    val lastlogin: Any,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("orgId")
    val orgId: String,
    @SerializedName("orgName")
    val orgName: String,
    @SerializedName("orgs")
    val orgs: List<Org>,
    @SerializedName("personId")
    val personId: String,
    @SerializedName("portait")
    val portait: String,
    @SerializedName("profiles")
    val profiles: List<Any>,
    @SerializedName("pwd")
    val pwd: String,
    @SerializedName("roles")
    val roles: List<Role>,
    @SerializedName("secondDept")
    val secondDept: String,
    @SerializedName("sex")
    val sex: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("trainingLevel")
    val trainingLevel: String,
    @SerializedName("utype")
    val utype: Int,
    @SerializedName("validdate")
    val validdate: Any,
    @SerializedName("validip")
    val validip: String
) : Serializable {
    data class Dept(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String
    )

    data class Org(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String
    )

    data class Role(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String
    )
}

data class MineUserInfoBean(
    @SerializedName("courseNum")
    val courseNum: String,
    @SerializedName("daystudy")
    val daystudy: Int,
    @SerializedName("exNum")
    val exNum: String,
    @SerializedName("examCount")
    val examCount: Int,
    @SerializedName("examList")
    val examList: List<Exam>,
    @SerializedName("loginday")
    val loginday: Int,
    @SerializedName("msgNum")
    val msgNum: String,
    @SerializedName("nowtime")
    val nowtime: String,
    @SerializedName("orderNum")
    val orderNum: Int,
    @SerializedName("simtime")
    val simtime: String,
    @SerializedName("tems")
    val tems: String,
    @SerializedName("userInfo")
    val userInfo: UserInfo,
    @SerializedName("wjdc")
    val wjdc: String
) : Serializable {
    data class Exam(
        @SerializedName("abid")
        val abid: Int,
        @SerializedName("bad_score")
        val badScore: Double,
        @SerializedName("begin_time")
        val beginTime: String,
        @SerializedName("disorder")
        val disorder: Int,
        @SerializedName("duration")
        val duration: Int,
        @SerializedName("end_time")
        val endTime: String,
        @SerializedName("epid")
        val epid: Int,
        @SerializedName("epstatus")
        val epstatus: Int,
        @SerializedName("examid")
        val examid: Int,
        @SerializedName("good_score")
        val goodScore: Double,
        @SerializedName("id")
        val id: Int,
        @SerializedName("kind")
        val kind: Int,
        @SerializedName("notice")
        val notice: Boolean,
        @SerializedName("opened")
        val opened: Int,
        @SerializedName("password")
        val password: String,
        @SerializedName("password_type")
        val passwordType: Int,
        @SerializedName("sceneid")
        val sceneid: Int,
        @SerializedName("score")
        val score: String,
        @SerializedName("simulate")
        val simulate: Int,
        @SerializedName("status")
        val status: Int,
        @SerializedName("subject_score")
        val subjectScore: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("tpid")
        val tpid: Int,
        @SerializedName("userid")
        val userid: Int
    )

    data class UserInfo(
        @SerializedName("address")
        val address: String,
        @SerializedName("birthday")
        val birthday: String,
        @SerializedName("creatime")
        val creatime: String,
        @SerializedName("creator")
        val creator: String,
        @SerializedName("deleted")
        val deleted: String,
        @SerializedName("deptName")
        val deptName: String,
        @SerializedName("deptid")
        val deptid: String,
        @SerializedName("edu")
        val edu: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("enabled")
        val enabled: Boolean,
        @SerializedName("endpointVersion")
        val endpointVersion: String,
        @SerializedName("flag")
        val flag: Int,
        @SerializedName("id")
        val id: String,
        @SerializedName("idcarda")
        val idcarda: String,
        @SerializedName("idcardb")
        val idcardb: String,
        @SerializedName("idcode")
        val idcode: String,
        @SerializedName("license")
        val license: String,
        @SerializedName("loginCode")
        val loginCode: String,
        @SerializedName("loginPass")
        val loginPass: String,
        @SerializedName("nation")
        val nation: String,
        @SerializedName("orgName")
        val orgName: String,
        @SerializedName("orgid")
        val orgid: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("portait")
        val portait: String,
        @SerializedName("post")
        val post: String,
        @SerializedName("rekpid")
        val rekpid: String,
        @SerializedName("rekpname")
        val rekpname: String,
        @SerializedName("rekppid")
        val rekppid: String,
        @SerializedName("remark")
        val remark: String,
        @SerializedName("sex")
        val sex: String,
        @SerializedName("skillLevel")
        val skillLevel: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("token")
        val token: String,
        @SerializedName("updater")
        val updater: String,
        @SerializedName("updatime")
        val updatime: String,
        @SerializedName("userName")
        val userName: String,
        @SerializedName("userType")
        val userType: String,
        @SerializedName("userno")
        val userno: String,
        @SerializedName("validate")
        val validate: String
    )
}

data class HomeDetailBean(
    @SerializedName("appFloat")
    val appFloat: AppFloat,
    @SerializedName("appphone")
    val appphone: String,
    @SerializedName("carousels")
    val carousels: List<Carousel>,
    @SerializedName("jpCourses")
    val jpCourses: List<JpCourse>,
    @SerializedName("lastCourse")
    val lastCourse: Any,
    @SerializedName("myCert")
//    val myCert: List<MyCert>,
    var myCert: Any,
    @SerializedName("payGateway")
    val payGateway: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("rmCarousels")
    val rmCarousels: List<Any>,
    @SerializedName("zrCourses")
    val zrCourses: List<JpCourse>,
    @SerializedName("wlCourses")
    val wlCourses: List<JpCourse>
) : Serializable {
    data class Carousel(
        @SerializedName("content")
        val content: String,
        @SerializedName("content_url")
        val contentUrl: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("thumb")
        val thumb: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String
    )

    data class JpCourse(
        @SerializedName("audience")
        val audience: String,
        @SerializedName("begin_time")
        val beginTime: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("cover")
        val cover: String,
        @SerializedName("creatime")
        val creatime: String,
        @SerializedName("creator")
        val creator: String,
        @SerializedName("csid")
        val csid: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("duration")
        val duration: Any,
        @SerializedName("enabled")
        val enabled: Int,
        @SerializedName("end_time")
        val endTime: String,
        @SerializedName("enroll_id")
        val enrollId: Int,
        @SerializedName("exercise")
        val exercise: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("notifid")
        val notifid: String,
        @SerializedName("opened")
        val opened: Int,
        @SerializedName("ordinal")
        val ordinal: Int,
        @SerializedName("origin")
        val origin: String,
//        @SerializedName("price")
//        val price: Double,
        @SerializedName("publish_time")
        val publishTime: String,
        @SerializedName("publisher")
        val publisher: String,
        @SerializedName("remark")
        val remark: String,
        @SerializedName("rs")
        val rs: Int,
        @SerializedName("sort_name")
        val sortName: String,
        @SerializedName("stars")
        val stars: Int,
        @SerializedName("status")
        val status: Int,
        @SerializedName("target")
        val target: String,
        @SerializedName("teacher")
        val teacher: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("updater")
        val updater: String,
        @SerializedName("updatime")
        val updatime: String
    )

    data class MyCert(
        @SerializedName("end_date")
        val endDate: String,
        @SerializedName("flag")
        val flag: Int,
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("publish_time")
        val publishTime: String,
        @SerializedName("receive_date")
        val receiveDate: String,
        @SerializedName("s_date")
        val sDate: String,
        @SerializedName("scode")
        val scode: String
    )

    data class AppFloat(
        @SerializedName("content")
        val content: String,
        @SerializedName("content_url")
        val contentUrl: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("thumb")
        val thumb: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String
    )
}

data class CourseTabItemBeanItem(
    @SerializedName("creatime")
    val creatime: String,
    @SerializedName("creator")
    val creator: String,
    @SerializedName("deleted")
    val deleted: Int,
    @SerializedName("enabled")
    val enabled: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("kind")
    val kind: Int,
    @SerializedName("num")
    val num: Int,
    @SerializedName("ordinal")
    val ordinal: Int,
    @SerializedName("pid")
    val pid: Int,
    @SerializedName("publish_time")
    val publishTime: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updater")
    val updater: String,
    @SerializedName("updatime")
    val updatime: String
)

data class CourseListBean(
    @SerializedName("grid")
    val grid: Grid,
    @SerializedName("msgNum")
    val msgNum: String,
    @SerializedName("prices")
    val prices: List<Price>
) : Serializable {
    data class Grid(
        @SerializedName("extra")
        val extra: String,
        @SerializedName("rows")
        val rows: List<Row>,
        @SerializedName("total")
        val total: Int
    )

    data class Price(
        @SerializedName("code")
        val code: String,
        @SerializedName("title")
        val title: String
    )

    data class Row(
        @SerializedName("audience")
        val audience: String,
        @SerializedName("begin_time")
        val beginTime: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("cover")
        val cover: String,
        @SerializedName("creatime")
        val creatime: String,
        @SerializedName("creator")
        val creator: String,
        @SerializedName("csid")
        val csid: String,
        @SerializedName("deleted")
        val deleted: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("discounts")
        val discounts: String,
        @SerializedName("duration")
        val duration: String,
        @SerializedName("enabled")
        val enabled: String,
        @SerializedName("end_time")
        val endTime: String,
        @SerializedName("end_time1")
        val endTime1: String,
        @SerializedName("enroll_id")
        val enrollId: String,
        @SerializedName("enroll_num")
        val enrollNum: String,
        @SerializedName("exercise")
        val exercise: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("notifid")
        val notifid: String,
        @SerializedName("opened")
        val opened: String,
        @SerializedName("ordinal")
        val ordinal: String,
        @SerializedName("origin")
        val origin: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("publish_time")
        val publishTime: String,
        @SerializedName("publisher")
        val publisher: String,
        @SerializedName("remark")
        val remark: String,
        @SerializedName("rs")
        val rs: Int,
        @SerializedName("sort_name")
        val sortName: String,
        @SerializedName("stars")
        val stars: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("study_num")
        val studyNum: Int,
        @SerializedName("target")
        val target: String,
        @SerializedName("teacher")
        val teacher: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("updater")
        val updater: String,
        @SerializedName("updatime")
        val updatime: String
    )
}

data class NoticeBean(
    @SerializedName("list")
    val list: NoticeData
) : Serializable {
    data class NoticeData(
        @SerializedName("extra")
        val extra: String,
        @SerializedName("rows")
        val rows: List<Row>,
        @SerializedName("total")
        val total: Int
    )

    data class Row(
        @SerializedName("content")
        val content: String,
        @SerializedName("content_url")
        val contentUrl: String,
        @SerializedName("creatime")
        val creatime: String,
        @SerializedName("creator")
        val creator: String,
        @SerializedName("deleted")
        val deleted: Int,
        @SerializedName("end_time")
        val endTime: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("keyword")
        val keyword: String,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("publish_time")
        val publishTime: String,
        @SerializedName("publisher")
        val publisher: String,
        @SerializedName("read_num")
        val readNum: Int,
        @SerializedName("remark")
        val remark: String,
        @SerializedName("start_time")
        val startTime: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("thumb")
        val thumb: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("updater")
        val updater: String,
        @SerializedName("updatime")
        val updatime: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("zan")
        val zan: Int,
        @SerializedName("zan_num")
        val zanNum: Int
    )
}

data class UserInfoBean(
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("deptId")
    val deptId: String?,
    @SerializedName("deptlList")
    val deptlList: List<Deptl>,
    @SerializedName("edu")
    var edu: String? = "",
    @SerializedName("edus")
    val edus: List<Edu>,
    @SerializedName("email")
    val email: String,
    @SerializedName("flag")
    val flag: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("idCode")
    val idCode: String,
    @SerializedName("idcarda")
    val idcarda: String,
    @SerializedName("idcardb")
    val idcardb: String,
    @SerializedName("license")
    val license: String,
    @SerializedName("loginCode")
    val loginCode: String,
    @SerializedName("nation")
    val nation: String?,
    @SerializedName("nations")
    val nations: List<Nation>,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("portait")
    val portait: String,
    @SerializedName("post")
    val post: String?,
    @SerializedName("posts")
    val posts: List<Post>,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("skillLevels")
    val skillLevels: List<SkillLevel>,
    @SerializedName("status")
    val status: Int,
    @SerializedName("userNO")
    val userNO: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("skillLevel")
    val skillLevel: String?,
    @SerializedName("studentid")
    val studentid: String?
) : Serializable {
    data class Deptl(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String
    ) : Serializable

    data class Edu(
        @SerializedName("code")
        val code: String,
        @SerializedName("editable")
        val editable: Boolean,
        @SerializedName("enabled")
        val enabled: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("ordinal")
        val ordinal: Int,
        @SerializedName("pid")
        val pid: Int,
        @SerializedName("scode")
        val scode: String,
        @SerializedName("title")
        val title: String
    ) : Serializable

    data class Nation(
        @SerializedName("code")
        val code: String,
        @SerializedName("editable")
        val editable: Boolean,
        @SerializedName("enabled")
        val enabled: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("ordinal")
        val ordinal: Int,
        @SerializedName("pid")
        val pid: Int,
        @SerializedName("scode")
        val scode: String,
        @SerializedName("title")
        val title: String
    ) : Serializable

    data class Post(
        @SerializedName("code")
        val code: String,
        @SerializedName("editable")
        val editable: Boolean,
        @SerializedName("enabled")
        val enabled: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("ordinal")
        val ordinal: Int,
        @SerializedName("pid")
        val pid: Int,
        @SerializedName("scode")
        val scode: String,
        @SerializedName("title")
        val title: String
    ) : Serializable

    data class SkillLevel(
        @SerializedName("code")
        val code: String,
        @SerializedName("editable")
        val editable: Boolean,
        @SerializedName("enabled")
        val enabled: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("ordinal")
        val ordinal: Int,
        @SerializedName("pid")
        val pid: Int,
        @SerializedName("scode")
        val scode: String,
        @SerializedName("title")
        val title: String
    ) : Serializable
}

data class SexBean(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String
)

data class DeptBeanItem(
    @SerializedName("allowed")
    val allowed: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("deleted")
    val deleted: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("enabled")
    val enabled: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("idpath")
    val idpath: String,
    @SerializedName("kind")
    val kind: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("ordinal")
    val ordinal: String,
    @SerializedName("pid")
    val pid: String,
    @SerializedName("shortName")
    val shortName: String
) : Serializable

data class MyCoursesBean(
    @SerializedName("grid")
    val grid: Grid,
    @SerializedName("msgNum")
    val msgNum: String,
    @SerializedName("nowTime")
    val nowTime: String,
    @SerializedName("prices")
    val prices: List<Price>
):Serializable{
    data class Grid(
        @SerializedName("extra")
        val extra: String,
        @SerializedName("rows")
        val rows: List<Row>,
        @SerializedName("total")
        val total: Int
    )

    data class Price(
        @SerializedName("code")
        val code: String,
        @SerializedName("title")
        val title: String
    )

    data class Row(
        @SerializedName("begin_time")
        val beginTime: String,
        @SerializedName("begindate")
        val begindate: String,
        @SerializedName("classid")
        val classid: String,
        @SerializedName("classname")
        val classname: String,
        @SerializedName("cover")
        val cover: String,
        @SerializedName("csid")
        val csid: String,
        @SerializedName("discounts")
        val discounts: String,
        @SerializedName("duration")
        val duration: String,
        @SerializedName("end_time")
        val endTime: String,
        @SerializedName("end_time1")
        val endTime1: String,
        @SerializedName("enroll_id")
        val enrollId: String,
        @SerializedName("enroll_num")
        val enrollNum: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("notifid")
        val notifid: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("sort_name")
        val sortName: String,
        @SerializedName("study_num")
        val studyNum: String,
        @SerializedName("teacher")
        val teacher: String,
        @SerializedName("title")
        val title: String
    )
}

data class QuestionDataBean(
    @SerializedName("kpid")
    val kpid: String,
    @SerializedName("kppid")
    val kppid: String,
    @SerializedName("recent")
    val recent: List<Recent>,
    @SerializedName("top")
    val top: List<Top>
):Serializable{
    data class Recent(
        @SerializedName("dts")
        val dts: String,
        @SerializedName("kpid")
        val kpid: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("xts")
        val xts: String,
        @SerializedName("zj")
        val zj: String
    )

    data class Top(
        @SerializedName("id")
        val id: String,
        @SerializedName("subKps")
        val subKps: List<SubKp>,
        @SerializedName("title")
        val title: String
    )

    data class SubKp(
        @SerializedName("id")
        val id: String,
        @SerializedName("title")
        val title: String
    )
}


data class UserQuestionInfoBean(
    @SerializedName("benr")
    val benr: String,
    @SerializedName("beny")
    val beny: String,
    @SerializedName("correct")
    val correct: String,
    @SerializedName("error")
    val error: String,
    @SerializedName("kpid")
    val kpid: String,
    @SerializedName("leij")
    val leij: String,
    @SerializedName("scs")
    val scs: String,
    @SerializedName("userid")
    val userid: String,
    @SerializedName("xts")
    val xts: String,
    @SerializedName("zql")
    val zql: String,
    @SerializedName("zuij")
    val zuij: String,
    @SerializedName("zwd")
    val zwd: String
):Serializable

data class SpecialExercisesBean(
    @SerializedName("judgeNum")
    val judgeNum: Int,
    @SerializedName("mulNum")
    val mulNum: Int,
    @SerializedName("obNum")
    val obNum: Int,
    @SerializedName("sinNum")
    val sinNum: Int
):Serializable

data class SpecialDetailBean(
    @SerializedName("exerid")
    val exerid: String,
    @SerializedName("ids")
    val ids: List<Id>,
    @SerializedName("questionDe")
    val questionDe: QuestionDe
):Serializable{
    data class Id(
        @SerializedName("id")
        val id: String
    )

    data class QuestionDe(
        @SerializedName("kpid")
        val kpid: String,
        @SerializedName("qkpid")
        val qkpid: String,
        @SerializedName("quOptions")
        val quOptions: List<QuOption>,
        @SerializedName("question")
        val question: Question
    )

    data class QuOption(
        @SerializedName("answer")
        val answer: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("qid")
        val qid: Int,
        @SerializedName("select")
        var select: Boolean,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        var type: String
    )

    data class Question(
        @SerializedName("analysis")
        val analysis: String,
        @SerializedName("answer")
        val answer: String,
        @SerializedName("collected")
        val collected: String,
        @SerializedName("creatime")
        val creatime: String,
        @SerializedName("creator")
        val creator: String,
        @SerializedName("deleted")
        val deleted: String,
        @SerializedName("difficulty")
        val difficulty: String,
        @SerializedName("enabled")
        val enabled: String,
        @SerializedName("flag")
        val flag: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("is_correct")
        val isCorrect: String,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("kpid")
        val kpid: String,
        @SerializedName("myAnswer")
        val myAnswer: String,
        @SerializedName("ordinal")
        val ordinal: String,
        @SerializedName("orgid")
        val orgid: String,
        @SerializedName("origin")
        val origin: String,
        @SerializedName("publish_time")
        val publishTime: String,
        @SerializedName("refcount")
        val refcount: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("updater")
        val updater: String,
        @SerializedName("updatime")
        val updatime: String
    )
}

data class SpecialSelectTypeBean(
    @SerializedName("id")
    val id: String,
    @SerializedName("answerType")
    var answerType: String
)

data class MyExamDetailBean(
    @SerializedName("nowtime")
    val nowtime: String,
    @SerializedName("rows")
    val rows: List<Row>,
    @SerializedName("simtime")
    val simtime: String,
    @SerializedName("total")
    val total: Int
)

data class Row(
    @SerializedName("abid")
    val abid: Int,
    @SerializedName("bad_score")
    val badScore: String,
    @SerializedName("begin_time")
    val beginTime: String,
    @SerializedName("checkface")
    val checkface: Int,
    @SerializedName("disorder")
    val disorder: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("epid")
    val epid: String,
    @SerializedName("epstatus")
    val epstatus: Int,
    @SerializedName("examid")
    val examid: Int,
    @SerializedName("first_face")
    val firstFace: String,
    @SerializedName("good_score")
    val goodScore: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("kind")
    val kind: Int,
    @SerializedName("opened")
    val opened: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_type")
    val passwordType: Int,
    @SerializedName("qnum")
    val qnum: Int,
    @SerializedName("sceneid")
    val sceneid: Int,
    @SerializedName("score")
    val score: String,
    @SerializedName("simulate")
    val simulate: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("subject_score")
    val subjectScore: String,
    @SerializedName("subject_status")
    val subjectStatus: String,
    @SerializedName("subscribe")
    val subscribe: Int,
    @SerializedName("system_status")
    val systemStatus: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("tpid")
    val tpid: Int,
    @SerializedName("userid")
    val userid: String,
    @SerializedName("yyflag")
    val yyflag: Int
)

