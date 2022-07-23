package com.czl.announcement

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.announcement.adapter.AnnounceItemAdapter
import com.czl.announcement.databinding.FragmentAnnouncementBinding
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.util.DayModeUtil
import com.gyf.immersionbar.ImmersionBar

@Route(path = AppConstants.Router.Announcement.F_ANNOU)
class AnnounceFragment:BaseFragment<FragmentAnnouncementBinding,AnnounceViewModel>() {

    private lateinit var announceItemAdapter: AnnounceItemAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_announcement
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun onSupportVisible() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)
            .statusBarColor(com.czl.lib_base.R.color.white)
            .fitsSystemWindows(true)
            .init()
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun reload() {
        super.reload()
        binding.smartRl.autoRefresh()
    }

    override fun initData() {
        viewModel.getNoticeList()

        iniAdapter()
    }

    override fun initViewObservable() {
        viewModel.uc.getNoticeDetail.observe(this,{
            handleRecyclerviewData(
                it == null,
                it as MutableList<*>?,
                announceItemAdapter,
                binding.shRv,
                binding.smartRl,
                viewModel.page,
                it?.isEmpty()
            )
        })
    }

    private fun iniAdapter(){
        announceItemAdapter = AnnounceItemAdapter(this)
        announceItemAdapter.setDiffCallback(announceItemAdapter.diffUtil)
        binding.shRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            adapter = announceItemAdapter
            showShimmerAdapter()
        }
    }
}