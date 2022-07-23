package com.czl.module_web.ui.fragment

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.util.SpHelper
import com.czl.module_web.BR
import com.czl.module_web.R
import com.czl.module_web.databinding.NewWebViewFragmentBinding
import com.czl.module_web.viewmodel.NewWebViewModel
import com.google.android.material.appbar.AppBarLayout
import com.gyf.immersionbar.ImmersionBar
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebConfig
import com.just.agentweb.NestedScrollAgentWebView
import com.just.agentweb.WebViewClient

/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 16:50

 * 描述:

 */
@Route(path = AppConstants.Router.Web.F_WEB_NA)
class NewAgentWebFragment : BaseFragment<NewWebViewFragmentBinding,NewWebViewModel>(){

    var agentWeb: AgentWeb? = null

    // 首页链接
    var homeUrl: String? = null

    var bundleUrl: String? = null

    var bundleId: String? = null

    var bundleNum: String? = null

    // 首页标题
    var homeTitle: String? = null
    var urlType: Int? = null

    override fun initContentView(): Int {
        return R.layout.new_web_view_fragment
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun onSupportVisible() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)
            .statusBarColor(com.czl.lib_base.R.color.white)
            .fitsSystemWindows(true)
            .init()
    }

    override fun onBackPressedSupport(): Boolean {
        agentWeb?:return super.onBackPressedSupport()
        if (agentWeb!!.webCreator.webView.canGoBack()) {
            agentWeb!!.back()
        } else {
            return super.onBackPressedSupport()
        }
        return true
    }


    override fun initParam() {
        bundleUrl = arguments?.getString(AppConstants.BundleKey.WEB_URL,"www.baidu.com")
        homeTitle = arguments?.getString(AppConstants.BundleKey.WEB_NAME, "新培在线")
        urlType = arguments?.getInt(AppConstants.BundleKey.WEB_TYPE, 0)
        bundleId = arguments?.getString(AppConstants.BundleKey.WEB_ITEM_ID)
    }

    override fun initData() {
        binding.tvTitleName.text = homeTitle

        Log.i("Url地址-->", bundleUrl)
        iniWebView()
    }

    private fun iniWebView(){
        val webView = NestedScrollAgentWebView(context)
        val lp = CoordinatorLayout.LayoutParams(-1, -1)
        lp.behavior = AppBarLayout.ScrollingViewBehavior()
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.clWebview, 1, lp)
            .useDefaultIndicator(
                ContextCompat.getColor(requireContext(),
                R.color.base_color_2e7bf7), 1)
            .setWebView(webView)
            .interceptUnkownUrl()
            .setWebViewClient(mWebClient)
            .additionalHttpHeader(AppConstants.Url.BASE_URL,"Token",SpHelper.decodeString(AppConstants.SpKey.APP_TOOKEN))
            .createAgentWeb()
            .ready()
            .go(bundleUrl)
        webView.settings.apply {
            //支持javascript
            javaScriptEnabled = true
            // 设置可以支持缩放
            setSupportZoom(true)
            // 设置出现缩放工具
            builtInZoomControls = true
            //扩大比例的缩放
            useWideViewPort = true
            //自适应屏幕
            loadWithOverviewMode = true
            displayZoomControls = false
        }
    }

    private val mWebClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?,
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

}