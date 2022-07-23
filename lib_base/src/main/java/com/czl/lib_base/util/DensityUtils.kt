package com.czl.lib_base.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.View.MeasureSpec
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.Field

object DensityUtils {
    /**
     * 获得屏幕的宽度 单位px
     *
     * @param context
     * @return
     */
    fun getHeightInPx(context: Context): Float {
        return context.resources.displayMetrics.heightPixels.toFloat()
    }

    /**
     * 获得屏幕的高度 单位px
     *
     * @param context
     * @return
     */
    fun getWidthInPx(context: Context): Float {
        return context.resources.displayMetrics.widthPixels.toFloat()
    }

    /**
     * 获得屏幕的高度 单位 dp
     *
     * @param context
     * @return
     */
    fun getHeightInDp(context: Context): Int {
        val height = context.resources.displayMetrics.heightPixels.toFloat()
        return px2dip(context, height)
    }

    /**
     * 获得屏幕的宽度 单位dp
     *
     * @param context
     * @return
     */
    fun getWidthInDp(context: Context): Int {
        val height = context.resources.displayMetrics.heightPixels.toFloat()
        return px2dip(context, height)
    }

    /**
     * 将单位dp的值转换为单位px的值
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将单位px的值转换为单位dp的值
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将单位px的值转换为单位sp的值
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将单位sp的值转换为单位px的值
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (spValue * scale + 0.5f).toInt()
    }

    /**
     * 获得状态栏高度 单位px
     *
     * @param context
     * @return
     */
    fun statusBarHeight(context: Context): Int {
        val frame = Rect()
        (context as Activity).window.decorView.getWindowVisibleDisplayFrame(frame)
        return frame.top
    }

    /**
     * 获得状态栏高度 单位px
     *
     * @param context
     * @return
     */
    fun statusBarHeight2(context: Context): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var sbar = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c.newInstance()
            field = c.getField("status_bar_height")
            x = field[obj].toString().toInt()
            sbar = context.resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
        return sbar
    }

    /**
     * 获得控件的宽高
     *
     * @param v
     * @return int[0] 宽  int[1] 高
     */
    fun getViewSize(view: View): IntArray {
        val rlp = view.layoutParams
        var childEndWidth = rlp.width
        var childEndHeight = rlp.height
        if (childEndWidth <= 0 || childEndHeight <= 0) {
            view.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            childEndWidth = view.measuredWidth
            childEndHeight = view.measuredHeight
        }
        return intArrayOf(childEndWidth, childEndHeight)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyboard(view: View?) {
        if (view != null) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
            view.clearFocus()
        }
    }

    /**
     * 打开软键盘
     *
     * @param view
     */
    fun openKeyboard(view: View?) {
        if (view != null) {
            view.requestFocus()
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }
    }
}