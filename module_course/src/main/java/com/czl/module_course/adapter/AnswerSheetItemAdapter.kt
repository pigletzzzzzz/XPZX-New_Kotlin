package com.czl.module_course.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.data.bean.SpecialSelectTypeBean
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.util.DensityUtils
import com.czl.lib_base.util.ToastHelper
import com.czl.module_course.R
import com.czl.module_course.R.color.base_color_2e7bf7
import com.czl.module_course.databinding.AdapterAnswerSheetBinding
import com.czl.module_course.databinding.AdapterAnswerSheetItemBinding
import com.czl.module_course.view.AnswerSheetPopView

/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 13:50

 * 描述:

 */
class AnswerSheetItemAdapter(var answerSheetPopView: AnswerSheetPopView):BaseQuickAdapter<SpecialSelectTypeBean,BaseDataBindingHolder<AdapterAnswerSheetItemBinding>>(
    R.layout.adapter_answer_sheet_item) {
    @SuppressLint("ResourceAsColor")
    override fun convert(
        holder: BaseDataBindingHolder<AdapterAnswerSheetItemBinding>,
        item: SpecialSelectTypeBean
    ) {
        holder.dataBinding?.apply {
            datas = item
            adapter = this@AnswerSheetItemAdapter

            tvNum.text = (holder.layoutPosition+1).toString()

            if (item.answerType == "1"){
                tvNum.setTextColor(com.czl.lib_base.R.color.base_color_2e7bf7)
                llAllView.setBackgroundResource(R.drawable.btn_corner_2e7bf7_6dp_l)
            }else{
                tvNum.setTextColor(com.czl.lib_base.R.color.base_color_ffe6e6)
                llAllView.setBackgroundResource(R.drawable.btn_corner_ffe6e6_6dp_l)
            }
        }
    }

    var onAnswerSheetItemClick:BindingCommand<Any> = BindingCommand(BindingConsumer {
        if (it is SpecialSelectTypeBean){
            answerSheetPopView.dismiss()
            LiveBusCenter.postPracticeIdPositionEvent(it.id,getItemPosition(it))
//            ToastHelper.showNormalToast("${it.id}--第${getItemPosition(it)}题")
        }
    })

    var diffUtil = object : DiffUtil.ItemCallback<SpecialSelectTypeBean>(){
        override fun areItemsTheSame(
            oldItem: SpecialSelectTypeBean,
            newItem: SpecialSelectTypeBean
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SpecialSelectTypeBean,
            newItem: SpecialSelectTypeBean
        ): Boolean {
            return oldItem.answerType == newItem.answerType
        }
    }
}