package com.czl.module_login.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.lib_base.data.bean.DeptBeanItem
import com.czl.module_login.R
import com.czl.module_login.databinding.DeptItemAdapterBinding
import com.czl.module_login.ui.fragment.AllDeptFragment

class DeptItemAdapter(val allDeptFragment: AllDeptFragment) :
    BaseQuickAdapter<DeptBeanItem, BaseDataBindingHolder<DeptItemAdapterBinding>>(
        R.layout.dept_item_adapter
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<DeptItemAdapterBinding>,
        item: DeptBeanItem
    ) {
        holder.dataBinding?.apply {
            datas = item
            adapter = this@DeptItemAdapter
        }
    }

    val diffUtil = object : DiffUtil.ItemCallback<DeptBeanItem>() {
        override fun areItemsTheSame(oldItem: DeptBeanItem, newItem: DeptBeanItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DeptBeanItem, newItem: DeptBeanItem): Boolean {
            return oldItem.code == newItem.code
        }
    }
}