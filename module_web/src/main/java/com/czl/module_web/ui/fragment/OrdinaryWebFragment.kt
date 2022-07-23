package com.czl.module_web.ui.fragment

import android.webkit.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.util.SpHelper
import com.czl.module_web.BR
import com.czl.module_web.R
import com.czl.module_web.databinding.OrdinaryWebViewFragmentBinding
import com.czl.module_web.viewmodel.OrdinaryWebViewModel
import com.gyf.immersionbar.ImmersionBar
import java.util.*

/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 17:32

 * 描述:

 */
@Route(path = AppConstants.Router.Web.F_WEB_OR)
class OrdinaryWebFragment:BaseFragment<OrdinaryWebViewFragmentBinding, OrdinaryWebViewModel>() {

    // 首页标题
    var homeTitle: String? = null
    // 首页链接
    var homeUrl: String? = null

    override fun initContentView(): Int {
        return R.layout.ordinary_web_view_fragment
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun onSupportVisible() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false, 0.2f)
            .statusBarColor(com.czl.lib_base.R.color.white)
            .fitsSystemWindows(true)
            .init()
    }

    override fun initParam() {
        homeUrl = arguments?.getString(AppConstants.BundleKey.WEB_URL, "www.baidu.com")
        homeTitle = arguments?.getString(AppConstants.BundleKey.WEB_NAME, "新培在线")
    }

    override fun initData() {
        binding.tvTitleName.text = homeTitle

        initWebView()
    }

    private fun initWebView(){
        val webSetting: WebSettings = binding.webView.settings
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(false)
        webSetting.loadWithOverviewMode = true
        webSetting.setAppCacheEnabled(true)
        webSetting.databaseEnabled = true
        webSetting.domStorageEnabled = true
        webSetting.javaScriptEnabled = true //支持js脚本
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE // 不加载缓存
        webSetting.setGeolocationEnabled(true)
        webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        webSetting.setAppCachePath(requireActivity().getDir("appcache", 0).path)
        webSetting.databasePath = requireActivity().getDir("databases", 0).path
        webSetting.setGeolocationDatabasePath(requireActivity().getDir("geolocation", 0).path)
        val header = HashMap<String, String>()
        header["Token"] = SpHelper.decodeString(AppConstants.SpKey.APP_TOOKEN)
        binding.webView.loadUrl(homeUrl, header)

        binding.webView.webViewClient = object :WebViewClient(){
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return super.shouldOverrideUrlLoading(view, url)
            }
        }

    }

    override fun onBackPressedSupport(): Boolean {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            return super.onBackPressedSupport()
        }
        return true
    }
}