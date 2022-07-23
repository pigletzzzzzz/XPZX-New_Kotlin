package com.czl.module_course.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.binding.command.BindingConsumer
import com.czl.lib_base.data.bean.SpecialDetailBean
import com.czl.module_course.R
import com.czl.module_course.databinding.AdapterPracticeItemBinding
import com.czl.module_course.fragment.PracticeTestFragment

class PracticeItemAdapter(val practiceTestFragment: PracticeTestFragment) :
    BaseQuickAdapter<SpecialDetailBean.QuOption, BaseDataBindingHolder<AdapterPracticeItemBinding>>(
        R.layout.adapter_practice_item
    ) {

    private var isShow = false //是否显示答案
    private var kind = 0 //题型
    private var answers = "" //用户选择的答案
    private var type = 0 //0每日一练 1背题模式

    override fun convert(
        holder: BaseDataBindingHolder<AdapterPracticeItemBinding>,
        item: SpecialDetailBean.QuOption
    ) {
        holder.dataBinding?.apply {
            var typeText = ""
            when (holder.layoutPosition) {
                0 -> typeText = "A"
                1 -> typeText = "B"
                2 -> typeText = "C"
                3 -> typeText = "D"
                4 -> typeText = "E"
                5 -> typeText = "F"
                6 -> typeText = "G"
                7 -> typeText = "H"
            }
            item.type = typeText

            datas = item
            adapter = this@PracticeItemAdapter

            tvType.text = typeText

            tvType.visibility = View.VISIBLE
            imgShow.visibility = View.GONE
            llPractice.setBackgroundResource(R.drawable.btn_corner_white_6dp)

            if (item.select) {
                llPractice.setBackgroundResource(R.drawable.btn_prac_select_6dp_lin)
            }

            if (isShow) {

                if (answers.isNotEmpty()) {
                    var ans = answers.split(",")
                    for (an: String in ans) {
                        if (an == item.type){
                            item.select = true
                        }
                    }
                }

                if (item.answer == 1) {
                    tvType.visibility = View.GONE
                    imgShow.visibility = View.VISIBLE
                    imgShow.setImageResource(R.mipmap.icon_tk_dt_dd)
                    llPractice.setBackgroundResource(R.drawable.btn_prac_select_true_6dp)
                } else {
                    if (item.answer == 0 && item.select) {
                        tvType.visibility = View.GONE
                        imgShow.visibility = View.VISIBLE
                        imgShow.setImageResource(R.mipmap.icon_tk_dt_dc)
                        llPractice.setBackgroundResource(R.drawable.btn_prac_select_false_6dp)
                    }
                }
            }
        }
    }

    val onItemClickCommand: BindingCommand<Any> = BindingCommand(BindingConsumer {
        if (answers.isNotEmpty() || type == 1){
            return@BindingConsumer
        }
        if (it is SpecialDetailBean.QuOption) {
            if (kind == 3) {
                it.select = !it.select
            } else {
                for (qu: SpecialDetailBean.QuOption in data) {
                    qu.select = qu.id == it.id
                }
                isShow = true
            }
            notifyDataSetChanged()
        }
    })

    var diffUtil = object : DiffUtil.ItemCallback<SpecialDetailBean.QuOption>() {
        override fun areItemsTheSame(
            oldItem: SpecialDetailBean.QuOption,
            newItem: SpecialDetailBean.QuOption
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SpecialDetailBean.QuOption,
            newItem: SpecialDetailBean.QuOption
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }

    fun setShow(show: Boolean, optionType:Int) {
        isShow = show
        type = optionType
    }

    fun setAnswer(answ: String) {
        answers = answ
    }

    fun setKind(k: Int) {
        kind = k
    }

}