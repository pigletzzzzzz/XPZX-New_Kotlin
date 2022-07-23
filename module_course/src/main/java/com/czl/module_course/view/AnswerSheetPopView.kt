package com.czl.module_course.view

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.data.bean.SpecialSelectTypeBean
import com.czl.lib_base.util.DensityUtils
import com.czl.lib_base.util.ToastHelper
import com.czl.lib_base.widget.GridSpaceItemDecoration
import com.czl.module_course.R
import com.czl.module_course.adapter.AnswerSheetItemAdapter
import com.czl.module_course.databinding.AdapterAnswerSheetBinding
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.util.XPopupUtils
import kotlinx.android.synthetic.main.adapter_answer_sheet.view.*

/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 13:24

 * 描述:添加答题卡弹窗

 */
class AnswerSheetPopView(val activity: BaseActivity<*, *>,var specialSelectTypeBeans:ArrayList<SpecialSelectTypeBean>) : CenterPopupView(activity) {

    private var dataBinding: AdapterAnswerSheetBinding? = null
    private lateinit var answerSheetItemAdapter: AnswerSheetItemAdapter

    override fun getImplLayoutId(): Int {
        return R.layout.adapter_answer_sheet
    }

    override fun onCreate() {
        super.onCreate()
        dataBinding = DataBindingUtil.bind(popupImplView)
        dataBinding?.apply {
            pop =this@AnswerSheetPopView
            ll_all_view.background = XPopupUtils.createDrawable(
                ContextCompat.getColor(context, com.czl.lib_base.R.color.white),
                10f, 10f, 10f, 10f
            )
        }

        initAnswerSheetView()

    }

    fun initAnswerSheetView(){
        answerSheetItemAdapter = AnswerSheetItemAdapter(this)
        answerSheetItemAdapter.setDiffCallback(answerSheetItemAdapter.diffUtil)
        dataBinding?.recyclerView.apply {
            this?.layoutManager = GridLayoutManager(context, 5)
            this?.addItemDecoration(GridSpaceItemDecoration(5,
                DensityUtils.dip2px(context,15f),DensityUtils.dip2px(context,23f)))
            this?.adapter = answerSheetItemAdapter
        }

        answerSheetItemAdapter.setDiffNewData(specialSelectTypeBeans)
    }

}