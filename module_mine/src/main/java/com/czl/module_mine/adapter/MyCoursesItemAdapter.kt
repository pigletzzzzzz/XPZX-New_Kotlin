package com.czl.module_mine.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.MyCoursesBean
import com.czl.lib_base.extension.loadRoundImage
import com.czl.module_mine.R
import com.czl.module_mine.databinding.AdapterMyCoursesItemBinding
import com.czl.module_mine.fragment.MyCoursesFragment

class MyCoursesItemAdapter(val myCoursesFragment: MyCoursesFragment) :
    BaseQuickAdapter<MyCoursesBean.Row, BaseDataBindingHolder<AdapterMyCoursesItemBinding>>(
        R.layout.adapter_my_courses_item
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<AdapterMyCoursesItemBinding>,
        item: MyCoursesBean.Row
    ) {
        holder.dataBinding?.apply {
            datas = item
            adapter = this@MyCoursesItemAdapter

            if (item.cover.isNotEmpty()) {
                imageView.loadRoundImage(AppConstants.Url.IMG_UPLOAD_URL + item.cover, 16)
            }
        }
    }

    val diffUtil = object : DiffUtil.ItemCallback<MyCoursesBean.Row>(){
        override fun areItemsTheSame(
            oldItem: MyCoursesBean.Row,
            newItem: MyCoursesBean.Row
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MyCoursesBean.Row,
            newItem: MyCoursesBean.Row
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }

}