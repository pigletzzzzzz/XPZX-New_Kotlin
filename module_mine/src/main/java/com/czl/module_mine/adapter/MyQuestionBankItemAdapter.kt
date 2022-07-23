package com.czl.module_mine.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.data.bean.QuestionDataBean
import com.czl.lib_base.util.DensityUtils
import com.czl.module_mine.R
import com.czl.module_mine.databinding.AdapterQuestionHistroyItemBinding
import com.czl.module_mine.fragment.MyQuestionBankFragment

class MyQuestionBankItemAdapter(val myQuestionBankFragment: MyQuestionBankFragment):BaseQuickAdapter<QuestionDataBean.Recent,BaseDataBindingHolder<AdapterQuestionHistroyItemBinding>>(
    R.layout.adapter_question_histroy_item) {
    override fun convert(
        holder: BaseDataBindingHolder<AdapterQuestionHistroyItemBinding>,
        item: QuestionDataBean.Recent
    ) {
        holder.dataBinding?.apply {
            datas = item
            adapter = this@MyQuestionBankItemAdapter

            val layoutParams = clAll.layoutParams
            layoutParams.width =
                ((DensityUtils.getWidthInPx(context) - DensityUtils.dip2px(context,41f))/2).toInt()
            clAll.layoutParams = layoutParams
        }
    }

    val diffUtil = object : DiffUtil.ItemCallback<QuestionDataBean.Recent>(){
        override fun areItemsTheSame(
            oldItem: QuestionDataBean.Recent,
            newItem: QuestionDataBean.Recent
        ): Boolean {
            return oldItem.kpid == newItem.kpid
        }

        override fun areContentsTheSame(
            oldItem: QuestionDataBean.Recent,
            newItem: QuestionDataBean.Recent
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }
}