package com.czl.module_course.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.CourseListBean
import com.czl.lib_base.extension.loadRoundImage
import com.czl.module_course.R
import com.czl.module_course.databinding.AdapterCourseItemBinding
import com.czl.module_course.fragment.CourseFragment

class CourseItemAdapter(val courseFragment: CourseFragment) :
    BaseQuickAdapter<CourseListBean.Row, BaseDataBindingHolder<AdapterCourseItemBinding>>(R.layout.adapter_course_item) {
    override fun convert(
        holder: BaseDataBindingHolder<AdapterCourseItemBinding>,
        item: CourseListBean.Row
    ) {
        holder.dataBinding?.apply {
            datas = item
            adapter = this@CourseItemAdapter

            tvNumberApplicants.text = "${item.rs}人报名"
            if (item.cover.isNotEmpty()) {
                imageView.loadRoundImage(AppConstants.Url.IMG_UPLOAD_URL + item.cover, 16)
            }
        }
    }

    val diffUtil = object : DiffUtil.ItemCallback<CourseListBean.Row>() {
        override fun areItemsTheSame(
            oldItem: CourseListBean.Row,
            newItem: CourseListBean.Row
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CourseListBean.Row,
            newItem: CourseListBean.Row
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}