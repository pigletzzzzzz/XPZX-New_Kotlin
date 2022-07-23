package com.czl.module_home.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.HomeDetailBean
import com.czl.lib_base.extension.loadRoundImage
import com.czl.module_home.R
import com.czl.module_home.fragment.HomeFragment
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter

class MyBannerAdapter(
    mData: List<HomeDetailBean.Carousel>,
    private val homeFragment: HomeFragment
) :BannerAdapter<HomeDetailBean.Carousel,MyBannerAdapter.ImageTitleHolder>(mData) {

    private var mDiffer: AsyncListDiffer<HomeDetailBean.Carousel>

    init {
        val diffCallback:DiffUtil.ItemCallback<HomeDetailBean.Carousel> = object :DiffUtil.ItemCallback<HomeDetailBean.Carousel>(){
            override fun areItemsTheSame(
                oldItem: HomeDetailBean.Carousel,
                newItem: HomeDetailBean.Carousel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HomeDetailBean.Carousel,
                newItem: HomeDetailBean.Carousel
            ): Boolean {
                return TextUtils.equals(oldItem.url,newItem.url)
            }
        }
        mDiffer = AsyncListDiffer(this,diffCallback)
    }


    class ImageTitleHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.image)
        var title: TextView = view.findViewById(R.id.bannerTitle)
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageTitleHolder {
        return ImageTitleHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.home_banner_item, parent, false)
        )
    }

    override fun onBindView(
        holder: ImageTitleHolder?,
        data: HomeDetailBean.Carousel?,
        position: Int,
        size: Int
    ) {
        holder?.imageView?.loadRoundImage(AppConstants.Url.IMG_UPLOAD_URL+data?.thumb,8)
        holder?.title?.text = data?.title
    }

    private fun submitData(data:List<HomeDetailBean.Carousel>?){
        mDiffer.submitList(data)
    }

    fun setData(banner: Banner<Any, BannerAdapter<*, *>>,data:List<HomeDetailBean.Carousel>){
        if (data == mDatas) {
            return
        }
        // 重新赋值新数据到banner
        setDatas(data)
        // 计算差异并赋值新数据
        submitData(data)
        // 设置banner
        banner.apply {
            setCurrentItem(1, false)
            setIndicatorPageChange()
        }
    }

}