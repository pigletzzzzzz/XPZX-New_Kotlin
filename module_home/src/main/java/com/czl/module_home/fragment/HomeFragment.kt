package com.czl.module_home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.HomeDetailBean
import com.czl.lib_base.util.DayModeUtil
import com.czl.module_home.BR
import com.czl.module_home.R
import com.czl.module_home.adapter.MyBannerAdapter
import com.czl.module_home.adapter.PopularCoursesAdapter
import com.czl.module_home.databinding.FragmentHomeBinding
import com.czl.module_home.viewmodel.HomeViewModel
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.gyf.immersionbar.ImmersionBar
import com.youth.banner.transformer.AlphaPageTransformer

@Route(path = AppConstants.Router.Home.F_HOME)
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private lateinit var bannerAdapter: MyBannerAdapter
    private lateinit var bannerSkeleton: SkeletonScreen
    private lateinit var myCertSkeleton: SkeletonScreen
    lateinit var popularCoursesAdapter: PopularCoursesAdapter
    lateinit var excellentCourseAdapter: PopularCoursesAdapter

    override fun initContentView(): Int {
        return R.layout.fragment_home
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingStatePage()
    }

    override fun initData() {
        initBannerView()
        initMyCertView()
        initPopularCoursesAdapter()
        initExcellentCourseAdapter()
    }

    override fun initViewObservable() {
        viewModel.uc.getHomeDetail.observe(this, {
            showSuccessStatePage()
            it?.apply {
                bannerSkeleton.hide()
                myCertSkeleton.hide()
                bannerAdapter = MyBannerAdapter(carousels,this@HomeFragment)
                binding.banner.adapter = bannerAdapter

                binding.rvPopularCourses.hideShimmerAdapter()
                popularCoursesAdapter.setDiffNewData(zrCourses as  MutableList<HomeDetailBean.JpCourse>?)

                binding.rvExcellentCourse.hideShimmerAdapter()
                excellentCourseAdapter.setDiffNewData(jpCourses as MutableList<HomeDetailBean.JpCourse>?)

                if (myCert is String){
                    binding.tvZslx.text = "证书类型：无"
                    binding.tvSxrq.text = "失效日期：无"
                }else if (myCert is List<*>){
                    var myCert:List<Map<String,String>> = myCert as List<Map<String, String>>
                    binding.tvZslx.text = "证书类型：${myCert.get(0)["name"]}"
                    binding.tvSxrq.text = "失效日期：${myCert.get(0)["end_date"]}"
                }
            }
        })
    }

    private fun initPopularCoursesAdapter() {
        popularCoursesAdapter = PopularCoursesAdapter(this)
        popularCoursesAdapter.setDiffCallback(popularCoursesAdapter.diffConfig)
        binding.rvPopularCourses.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = popularCoursesAdapter
            setDemoLayoutReference(R.layout.adapter_courses_item_skeleton)
            setDemoLayoutManager(ShimmerRecyclerView.LayoutMangerType.LINEAR_VERTICAL)
            showShimmerAdapter()
        }
    }

    private fun initExcellentCourseAdapter() {
        excellentCourseAdapter = PopularCoursesAdapter(this)
        excellentCourseAdapter.setDiffCallback(excellentCourseAdapter.diffConfig)
        binding.rvExcellentCourse.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = excellentCourseAdapter
            setDemoLayoutReference(R.layout.adapter_courses_item_skeleton)
            setDemoLayoutManager(ShimmerRecyclerView.LayoutMangerType.LINEAR_VERTICAL)
            showShimmerAdapter()
        }
    }

    private fun initMyCertView() {
        myCertSkeleton = Skeleton.bind(binding.clZsxx)
            .load(R.layout.home_mycert_skeleton)
            .show()
    }

    private fun initBannerView() {
        bannerSkeleton = Skeleton.bind(binding.banner)
            .load(R.layout.home_banner_skeleton)
            .show()

        binding.banner.apply {
            addBannerLifecycleObserver(this@HomeFragment)
            //            setBannerGalleryMZ(20)
            setBannerGalleryEffect(18, 10)
            addPageTransformer(AlphaPageTransformer(0.6f))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHomeDetail()
    }
}