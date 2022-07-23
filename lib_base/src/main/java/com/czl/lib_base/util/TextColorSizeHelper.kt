package com.czl.lib_base.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ReplacementSpan
import android.util.Log
import android.view.View
import androidx.annotation.NonNull


/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 17:11

 * 描述:

 */
class TextColorSizeHelper {

    /**
     * 更改字体大小、颜色、增加点击事件
     *
     * @param context
     * @param text
     * @param spanInfos
     * @return
     */

    open fun getTextSpan(
        context: Context?,
        text: String,
        spanInfos: List<SpanInfo?>?
    ): SpannableStringBuilder? {
        if (context == null || TextUtils.isEmpty(text)
            || spanInfos == null || spanInfos.isEmpty()
        ) {
            return null
        }
        val style = SpannableStringBuilder(text)
        var begin = 0
        var end = 0
        for (i in spanInfos.indices) {
            val spanBean: SpanInfo? = spanInfos[i]
            //
            if (spanBean != null && !TextUtils.isEmpty(spanBean.subText)) {
                // 从后边向前寻找
                if (spanBean.findFromEnd) {
                    begin = text.lastIndexOf(spanBean.subText!!)
                } else {
                    begin = text.indexOf(spanBean.subText!!, end)
                }
                end = begin + spanBean.subText.length
                // 字号有变动
                if (spanBean.subTextSize > 0) {
                    style.setSpan(
                        AbsoluteSizeSpan(spanBean.subTextSize),
                        begin,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                // 字体颜色
                if (spanBean.subTextColor !== 0) {
                    // 背景圆角
                    if (spanBean.subTextBgRadius > 0 && spanBean.subTextBgColor !== 0) {
                        style.setSpan(
                            RadiusBackgroundSpan(
                                spanBean.subTextColor,
                                spanBean.subTextBgColor,
                                spanBean.subTextBgRadius
                            ), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    // 字体点击
                    if (spanBean.clickableSpan != null) {
                        style.setSpan(object : ClickableSpan() {
                            override fun updateDrawState(ds: TextPaint) {
                                // set textColor
                                ds.color = spanBean.subTextColor
                                // 下划线
                                ds.isUnderlineText = spanBean.clickSpanUnderline
                            }

                            override fun onClick(widget: View) {
                                if (spanBean.clickableSpan != null) {
                                    spanBean.clickableSpan.onClick(widget)
                                }
                            }
                        }, begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    } else {
                        style.setSpan(
                            ForegroundColorSpan(spanBean.subTextColor),
                            begin,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }
        }
        return style
    }

    class SpanInfo(// 文字
        val subText: String?, // 像素
        var subTextSize: Int, subTextColor: Int,
        subTextBgColor: Int, subTextBgRadius: Int,
        clickableSpan: ClickableSpan?, clickSpanUnderline: Boolean,
        findFromEnd: Boolean
    ) {
        // Color.parseColor("#af5050");
        val subTextColor: Int

        //
        // sub txt背景相关
        val subTextBgColor: Int
        val subTextBgRadius: Int

        //
        // 点击相关
        val clickableSpan: ClickableSpan?

        // 是否展示下划线
        val clickSpanUnderline: Boolean

        //
        // 从前向后寻找 or 从后向前寻找
        val findFromEnd: Boolean

        // 不展示背景圆角 不可点击
        constructor(
            subText: String?, subTextSize: Int, subTextColor: Int,
            findFromEnd: Boolean
        ) : this(
            subText, subTextSize, subTextColor,
            0, -1,
            null, false, findFromEnd
        )

        // 不展示背景圆角 可点击
        constructor(
            subText: String?, subTextSize: Int, subTextColor: Int,
            clickableSpan: ClickableSpan?, clickSpanUnderline: Boolean,
            findFromEnd: Boolean
        ) : this(
            subText, subTextSize, subTextColor,
            0, -1,
            clickableSpan, clickSpanUnderline, findFromEnd
        )

        // 展示背景圆角 不可点击
        constructor(
            subText: String?, subTextSize: Int, subTextColor: Int,
            subTextBgColor: Int, subTextBgRadius: Int,
            findFromEnd: Boolean
        ) : this(
            subText, subTextSize, subTextColor,
            subTextBgColor, subTextBgRadius,
            null, false, findFromEnd
        )

        override fun toString(): String {
            val sb = StringBuffer()
            sb.append("subText: ")
            sb.append(subText)
            sb.append(" subTextSize: ")
            sb.append(subTextSize)
            sb.append(" findFromEnd: ")
            sb.append(findFromEnd)
            return sb.toString()
        }

        // 展示背景圆角
        init {
            // sub txt
            subTextSize = subTextSize
            this.subTextColor = subTextColor
            // sub txt背景相关
            this.subTextBgColor = subTextBgColor
            this.subTextBgRadius = subTextBgRadius
            // 点击相关
            this.clickableSpan = clickableSpan
            this.clickSpanUnderline = clickSpanUnderline
            // 从前向后寻找 or 从后向前寻找
            this.findFromEnd = findFromEnd
        }
    }

    class RadiusBackgroundSpan
    /**
     * @param radiusBgColor 背景颜色
     * @param radius        圆角半径
     */(private val mTextColor: Int, private val mRadiusBgColor: Int, private val mRadius: Int) :
        ReplacementSpan() {
        private var mSize = 0
        override fun getSize(
            paint: Paint,
            text: CharSequence?,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
        ): Int {
            mSize = ((paint.measureText(text, start, end) + mRadius).toInt())
            return mSize
        }

        override fun draw(
            canvas: Canvas,
            text: CharSequence?,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            val color: Int = paint.color //保存文字颜色
            paint.color = mRadiusBgColor //设置背景颜色
            paint.isAntiAlias = true // 设置画笔的锯齿效果
            Log.i("pyt", y.toString() + "")
            val oval = RectF(x, y + paint.ascent(), x + mSize, y + paint.descent())
            //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
            canvas.drawRoundRect(oval, mRadius.toFloat(), mRadius.toFloat(), paint) //绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
            paint.color = mTextColor //恢复画笔的文字颜色
            canvas.drawText(text.toString(), start, end, x + mRadius / 2, y.toFloat(), paint) //绘制文字
        }
    }

}