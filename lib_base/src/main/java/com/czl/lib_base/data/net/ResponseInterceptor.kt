package com.czl.lib_base.data.net

import com.blankj.utilcode.util.GsonUtils
import com.czl.lib_base.base.BaseBean
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.UserQuestionInfoBean
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.util.SpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.http.HttpHeaders
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * @author Alwyn
 * @Date 2020/8/4
 * @Description 对服务器返回结果处理解密 token失效等
 */
class ResponseInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        val responseBody = response.body()
        val url = request.url().toString()
        if (HttpHeaders.hasBody(response)) {
            if (responseBody == null) return response
            val mediaType = responseBody.contentType()
            when (response.code()) {
                503, 504, 500, 404, 403, 400 -> {
                    response = response.newBuilder().code(response.code()).message("连接服务器失败，请稍后再试").build()
                }
                200 -> {
                    val source = responseBody.source()
                    source.request(Long.MAX_VALUE)
                    val buffer = source.buffer
                    val charset = StandardCharsets.UTF_8
                    //                    charset = mediaType.charset(charset);
                    if (charset != null) {
                        // 服务器返回结果 可处理加解密或者token失效
                        val bodyString = buffer.clone().readString(charset)
                        if (!url.contains("xpzx/front/allDept")){
                            val baseBean = Gson().fromJson<BaseBean<*>>(
                                bodyString, object : TypeToken<BaseBean<*>?>() {}.type)
//                        LogUtils.i("Interceptor Response=" + baseBean.toString() + ",code=" + baseBean.errorCode)
                            when (baseBean.msg) {
                                "未登录,请登录系统" -> {//身份验证失败
                                    // token/cookie失效 ApiSubscriberHelper 已在ApiSubscriberHelper网络业务层处理
                                    response =
                                        response.newBuilder().code(300).message("未登录,请登录系统").build()
                                    LiveBusCenter.postTokenExpiredEvent(baseBean.msg)
                                    SpHelper.encode(AppConstants.SpKey.IS_LOGIN,false)
                                }
                            }

                            if (url.contains("xpzx/front/exercise/kpsTreeV3")){//如果是题库详情接口则获取 extra里面的数据
                                LiveBusCenter.postQuestionInfoEvent(GsonUtils.toJson(baseBean.extra))
                            }

                            //如果是登录接口返回200，则保存msg中的token
                            if (url.contains("app/doLogin")){
                                SpHelper.encode(AppConstants.SpKey.APP_TOOKEN, baseBean.msg.toString())
                            }
                        }

                        val newRespBody = ResponseBody.create(mediaType, bodyString)
                        response = response.newBuilder().body(newRespBody).build()
                    }
                }
            }
        }
        return response
    }

    private fun isPlaintext(mediaType: MediaType?): Boolean {
        if (mediaType == null) return false
        if (mediaType.type() == "text") {
            return true
        }
        var subtype = mediaType.subtype()
        subtype = subtype.toLowerCase(Locale.getDefault())
        return subtype.contains("x-www-form-urlencoded")
                || subtype.contains("json")
                || subtype.contains("xml")
                || subtype.contains("html")
    }
}