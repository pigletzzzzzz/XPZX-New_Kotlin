package com.czl.announcement.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.czl.announcement.AnnounceFragment
import com.czl.announcement.R
import com.czl.announcement.databinding.AdapterAnnounceItemBinding
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.NoticeBean
import com.czl.lib_base.extension.loadRoundImage

class AnnounceItemAdapter(val announceFragment: AnnounceFragment) :
    BaseQuickAdapter<NoticeBean.Row, BaseDataBindingHolder<AdapterAnnounceItemBinding>>(
        R.layout.adapter_announce_item
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<AdapterAnnounceItemBinding>,
        item: NoticeBean.Row
    ) {
        holder.dataBinding?.apply {
            datas = item
            adapter = this@AnnounceItemAdapter

            if (item.zan == 0){
                imgLike.setImageResource(R.mipmap.icon_like_n)
            }else{
                imgLike.setImageResource(R.mipmap.icon_like_y)
            }

            if (item.thumb.isNotEmpty()){
                imageView.loadRoundImage(AppConstants.Url.IMG_UPLOAD_URL + item.thumb, 16)
            }
        }
    }

    val diffUtil = object :DiffUtil.ItemCallback<NoticeBean.Row>(){
        override fun areItemsTheSame(oldItem: NoticeBean.Row, newItem: NoticeBean.Row): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoticeBean.Row, newItem: NoticeBean.Row): Boolean {
            return oldItem.title == newItem.title
        }
    }

}