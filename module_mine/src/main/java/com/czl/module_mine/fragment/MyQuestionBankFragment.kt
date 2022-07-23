package com.czl.module_mine.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.GsonUtils
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.*
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.util.ToastHelper
import com.czl.module_mine.BR
import com.czl.module_mine.R
import com.czl.module_mine.adapter.MyQuestionBankItemAdapter
import com.czl.module_mine.databinding.FragmentMyQuestionBankBinding
import com.czl.module_mine.viewmodel.MyQuestionBankViewModel
import com.google.gson.reflect.TypeToken
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener

@Route(path = AppConstants.Router.Mine.F_MY_QUESTION_BANK)
class MyQuestionBankFragment :
    BaseFragment<FragmentMyQuestionBankBinding, MyQuestionBankViewModel>() {

    private lateinit var myQuestionBankItemAdapter: MyQuestionBankItemAdapter
    private var specialExercisesBean = SpecialExercisesBean(0, 0, 0, 0)

    override fun initContentView(): Int {
        return R.layout.fragment_my_question_bank
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun onSupportVisible() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false, 0.2f)
            .statusBarColor(com.czl.lib_base.R.color.base_color_2e7bf7)
            .fitsSystemWindows(true)
            .init()
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initData() {
//        viewModel.getKpsTreeData()

        initMyQuestionBankAdapter()
    }

    override fun initViewObservable() {
        viewModel.uc.onQuestionDataEvent.observe(this, {
            if (it != null) {
                viewModel.akpid.set(it.kpid)

                it.apply {
                    binding.rvList.hideShimmerAdapter()
                    if (recent.isNotEmpty()) {
                        binding.tvGzName.text = recent[0].name
                        viewModel.knowledgeName.set(recent[0].name)

                        binding.rvList.hideShimmerAdapter()
                        myQuestionBankItemAdapter.setDiffNewData(recent as MutableList<QuestionDataBean.Recent>?)
                    } else {
                        val emptyView = View.inflate(
                            context,
                            com.czl.lib_base.R.layout.common_empty_layout,
                            null
                        )
                        emptyView.findViewById<ViewGroup>(com.czl.lib_base.R.id.ll_empty)
                            .setOnClickListener {
                                viewModel.getKpsTreeData()
                            }
                        myQuestionBankItemAdapter.setEmptyView(emptyView)
                    }
                }

            } else {
                binding.tvGzName.text = "请选择知识点"
                binding.tvDtNum.text = "0"
                binding.tvDtNumAll.text = "/0"
                binding.tvZqlNum.text = "0"
                binding.tvZqlNum.text = "0"

                binding.rvList.hideShimmerAdapter()

                val emptyView =
                    View.inflate(context, com.czl.lib_base.R.layout.common_empty_layout, null)
                emptyView.findViewById<ViewGroup>(com.czl.lib_base.R.id.ll_empty)
                    .setOnClickListener {
                        viewModel.getKpsTreeData()
                    }
                myQuestionBankItemAdapter.setEmptyView(emptyView)
            }
        })

        LiveBusCenter.observeQuestionInfoEvent(this, {
            if (it.info?.isNotEmpty() == true) {
                val userQuestionDataBean: UserQuestionInfoBean =
                    GsonUtils.fromJson(it.info, object : TypeToken<UserQuestionInfoBean>() {}.type)

                userQuestionDataBean.apply {
                    binding.tvDtNum.text = leij.split(".")[0]
                    binding.tvDtNumAll.text = "/${xts.split(".")[0]}"
                    binding.tvZqlNum.text = zql
                    binding.tvZwdNum.text = zwd

                    viewModel.wrongQuesNum.set(error.split(".")[0].toInt())
                    viewModel.collectNum.set(scs.split(".")[0].toInt())
                }

            }
        })

        LiveBusCenter.observeKnowledgeSelectEvent(this, {
            viewModel.akpid.set(it.id)
            binding.tvGzName.text = it.name
            viewModel.knowledgeName.set(it.name)
            viewModel.getSpecialPracticeNum()
        })

        viewModel.uc.onSpecialExercisesEvent.observe(this, {
            specialExercisesBean = SpecialExercisesBean(it.judgeNum, it.mulNum, it.obNum, it.sinNum)
        })

        viewModel.uc.onShowSpecialExercisesEvent.observe(this, {
            showSpecialExercisesDialog()
        })

        myQuestionBankItemAdapter.setOnItemClickListener { adapter, view, position ->
            var dataBean = adapter.data[position] as QuestionDataBean.Recent
            viewModel.akpid.set(dataBean.kpid)
            viewModel.getKpsTreeData()
        }
    }


    private fun initMyQuestionBankAdapter() {
        myQuestionBankItemAdapter = MyQuestionBankItemAdapter(this)
        myQuestionBankItemAdapter.setDiffCallback(myQuestionBankItemAdapter.diffUtil)
        binding.rvList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = myQuestionBankItemAdapter
            setDemoLayoutReference(R.layout.adapter_question_item_skeleton)
            setDemoLayoutManager(ShimmerRecyclerView.LayoutMangerType.LINEAR_VERTICAL)
            showShimmerAdapter()
        }
    }

    private fun showSpecialExercisesDialog() {
        XPopup.Builder(context)
            .isDarkTheme(false)
            .hasShadowBg(true)
            .asCenterList(
                "请选择需要练习的题型",
                arrayOf(
                    "单选题(${specialExercisesBean.sinNum})",
                    "多选题(${specialExercisesBean.mulNum})",
                    "判断题(${specialExercisesBean.judgeNum})",
                    "主观题(${specialExercisesBean.obNum})"
                )
            ) { position, text ->
                when(position){
                    0 -> {
                        if (specialExercisesBean.sinNum == 0){
                            ToastHelper.showErrorToast("当前没有单选题！")
                            return@asCenterList
                        }
                        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
                            putString(AppConstants.BundleKey.KNOWLEDGE_ID,viewModel.akpid.get()!!)
                            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE,"专项练习")
                            putString(AppConstants.BundleKey.SPECIAL_TYPE,"1")
                            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID,3)
                            putString(AppConstants.BundleKey.KNOWLEDGE_NAME,viewModel.knowledgeName.get()!!)
                        })
                    }
                    1 -> {
                        if (specialExercisesBean.mulNum == 0){
                            ToastHelper.showErrorToast("当前没有多选题！")
                            return@asCenterList
                        }
                        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
                            putString(AppConstants.BundleKey.KNOWLEDGE_ID,viewModel.akpid.get()!!)
                            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE,"专项练习")
                            putString(AppConstants.BundleKey.SPECIAL_TYPE,"3")
                            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID,3)
                            putString(AppConstants.BundleKey.KNOWLEDGE_NAME,viewModel.knowledgeName.get()!!)
                        })
                    }
                    2 -> {
                        if (specialExercisesBean.judgeNum == 0){
                            ToastHelper.showErrorToast("当前没有判断题！")
                            return@asCenterList
                        }
                        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
                            putString(AppConstants.BundleKey.KNOWLEDGE_ID,viewModel.akpid.get()!!)
                            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE,"专项练习")
                            putString(AppConstants.BundleKey.SPECIAL_TYPE,"2")
                            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID,3)
                            putString(AppConstants.BundleKey.KNOWLEDGE_NAME,viewModel.knowledgeName.get()!!)
                        })
                    }
                    3 -> {
                        if (specialExercisesBean.obNum == 0){
                            ToastHelper.showErrorToast("当前没有主观题！")
                            return@asCenterList
                        }
                        startContainerActivity(AppConstants.Router.Course.F_PRACTICE, Bundle().apply {
                            putString(AppConstants.BundleKey.KNOWLEDGE_ID,viewModel.akpid.get()!!)
                            putString(AppConstants.BundleKey.KNOWLEDGE_TYPE,"专项练习")
                            putString(AppConstants.BundleKey.SPECIAL_TYPE,"6")
                            putInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID,3)
                            putString(AppConstants.BundleKey.KNOWLEDGE_NAME,viewModel.knowledgeName.get()!!)
                        })
                    }
                }
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getKpsTreeData()
    }

}