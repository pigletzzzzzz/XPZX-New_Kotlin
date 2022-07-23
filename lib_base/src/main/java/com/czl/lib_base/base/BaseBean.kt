package com.czl.lib_base.base

import java.io.Serializable


/**
 * @author Alwyn
 * @Date 2019/9/10
 * @Description
 */
class BaseBean<T> : Serializable {
    var data: T? = null
    var msg: String? = null
    var extra: Any? = null
    var success: Boolean? = null


    companion object {
        private const val serialVersionUID = 5223230387175917834L
    }

    override fun toString(): String {
        return "BaseBean(data=$data, extra=$extra, msg=$msg,success=$success)"
    }

}
