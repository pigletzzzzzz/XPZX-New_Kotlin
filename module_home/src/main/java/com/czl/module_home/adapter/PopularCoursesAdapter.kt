package com.czl.module_home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.HomeDetailBean
import com.czl.lib_base.extension.loadRoundImage
import com.czl.lib_base.util.DensityUtils
import com.czl.module_home.R
import com.czl.module_home.databinding.AdapterCoursesItemBinding
import com.czl.module_home.fragment.HomeFragment

class PopularCoursesAdapter(val homeFragment: HomeFragment) :
    BaseQuickAdapter<HomeDetailBean.JpCourse, BaseDataBindingHolder<AdapterCoursesItemBinding>>(
        R.layout.adapter_courses_item
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<AdapterCoursesItemBinding>,
        item: HomeDetailBean.JpCourse
    ) {
        holder.dataBinding?.apply {
            data = item
            adapter = this@PopularCoursesAdapter

            if (item.cover.isNotEmpty()) {
                imgKc.loadRoundImage(AppConstants.Url.IMG_UPLOAD_URL + item.cover, 16)
            }

            val layoutParams = clAllView.layoutParams
            layoutParams.width =
                ((DensityUtils.getWidthInPx(context) - DensityUtils.dip2px(context,41f))/2).toInt()
            clAllView.layoutParams = layoutParams
        }
    }

    val diffConfig = object : DiffUtil.ItemCallback<HomeDetailBean.JpCourse>() {
        override fun areItemsTheSame(
            oldItem: HomeDetailBean.JpCourse,
            newItem: HomeDetailBean.JpCourse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HomeDetailBean.JpCourse,
            newItem: HomeDetailBean.JpCourse
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}