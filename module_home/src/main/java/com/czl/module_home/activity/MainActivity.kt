package com.czl.module_home.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.czl.lib_base.adapter.ViewPagerFmAdapter
import com.czl.lib_base.base.AppManager
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.route.RouteCenter
import com.czl.module_home.BR
import com.czl.module_home.R
import com.czl.module_home.databinding.ActivityMainBinding
import com.czl.module_home.viewmodel.MainViewModel
import me.yokeyword.fragmentation.SupportFragment

@Route(path = AppConstants.Router.Home.A_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private var touchTime: Long = 0L

    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initViewObservable() {
        viewModel.uc.tabChangeLiveEvent.observe(this,{
            binding.viewPager2.setCurrentItem(it,false)
        })

        viewModel.uc.pageChangeLiveEvent.observe(this,{
            binding.bottomBar.selectTab(it)
        })
    }

    override fun initData() {
        initBottomBar()
        initViewPager()
    }

    private fun initViewPager(){
        binding.viewPager2.isUserInputEnabled = false
        val homeFragment = RouteCenter.navigate(AppConstants.Router.Home.F_HOME) as SupportFragment//首页
        val courseFragment = RouteCenter.navigate(AppConstants.Router.Course.F_COURSE) as SupportFragment//课程
        val announceFragment = RouteCenter.navigate(AppConstants.Router.Announcement.F_ANNOU) as SupportFragment//公告
        val mineFragment = RouteCenter.navigate(AppConstants.Router.Mine.F_MINE) as SupportFragment//个人中心
        val fragments = arrayListOf(homeFragment,courseFragment,announceFragment,mineFragment)
        binding.viewPager2.apply {
            adapter = ViewPagerFmAdapter(supportFragmentManager,lifecycle,fragments)
            offscreenPageLimit = fragments.size
        }
    }

    private fun initBottomBar() {
        binding.bottomBar.apply {
            setMode(BottomNavigationBar.MODE_FIXED)
            setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            addItem(
                BottomNavigationItem(
                    R.mipmap.icon_sy_y,
                    R.string.home_sy
                ).setActiveColorResource(com.czl.lib_base.R.color.base_color_2284FA)
                    .setInactiveIconResource(R.mipmap.icon_sy_n)
            )

            addItem(
                BottomNavigationItem(
                    R.mipmap.icon_kc_y,
                    R.string.home_kc
                ).setActiveColorResource(com.czl.lib_base.R.color.base_color_2284FA)
                    .setInactiveIconResource(R.mipmap.icon_kc_n)
            )

            addItem(
                BottomNavigationItem(
                    R.mipmap.icon_gg_y,
                    R.string.home_gg
                ).setActiveColorResource(com.czl.lib_base.R.color.base_color_2284FA)
                    .setInactiveIconResource(R.mipmap.icon_gg_n)
            )

            addItem(
                BottomNavigationItem(
                    R.mipmap.icon_wd_y,
                    R.string.home_wd
                ).setActiveColorResource(com.czl.lib_base.R.color.base_color_2284FA)
                    .setInactiveIconResource(R.mipmap.icon_wd_n)
            )
            setFirstSelectedPosition(0)
            initialise()
        }
    }

    override fun onBackPressedSupport() {
        if (System.currentTimeMillis() - touchTime < 2000L) {
            AppManager.instance.appExit()
        } else {
            touchTime = System.currentTimeMillis()
            showNormalToast(getString(R.string.home_main_press_again))
        }
    }
}