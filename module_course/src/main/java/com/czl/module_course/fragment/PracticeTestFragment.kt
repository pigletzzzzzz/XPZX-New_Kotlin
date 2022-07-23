package com.czl.module_course.fragment

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.AppManager
import com.czl.lib_base.base.BaseActivity
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.SpecialDetailBean
import com.czl.lib_base.data.bean.SpecialSelectTypeBean
import com.czl.lib_base.event.LiveBusCenter
import com.czl.lib_base.util.ToastHelper
import com.czl.module_course.BR
import com.czl.module_course.R
import com.czl.module_course.adapter.PracticeItemAdapter
import com.czl.module_course.databinding.FragmentPracticeTestBinding
import com.czl.module_course.view.AnswerSheetPopView
import com.czl.module_course.viewmodel.PracticeTestViewModel
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.CenterPopupView

@Route(path = AppConstants.Router.Course.F_PRACTICE)
class PracticeTestFragment : BaseFragment<FragmentPracticeTestBinding, PracticeTestViewModel>() {

    private lateinit var practiceItemAdapter: PracticeItemAdapter
    private var specialSelectTypeBeans = arrayListOf<SpecialSelectTypeBean>()
    private var knowledgeName = ""//知识点名称
    private var knowledgeId = ""//知识点id
    private var knowledgeType = ""//练习类别
    private var specialType = ""//专项练习类别 3多选 1单选 2判断 6主观
    private var knowledgeTypeId = 0//练习类别id  0为每日一练  1为背题模式 2为未练习题 3专项练习 4错题本 5收藏夹
    private var kind = -1;//题目类型
    private var nowQuestionNum = 0;//当前题目位置

    override fun initContentView(): Int {
        return R.layout.fragment_practice_test
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return true
    }

    override fun initData() {
        knowledgeId = arguments?.getString(AppConstants.BundleKey.KNOWLEDGE_ID).toString()
        knowledgeName = arguments?.getString(AppConstants.BundleKey.KNOWLEDGE_NAME).toString()
        knowledgeType = arguments?.getString(AppConstants.BundleKey.KNOWLEDGE_TYPE).toString()
        specialType = arguments?.getString(AppConstants.BundleKey.SPECIAL_TYPE, "").toString()
        knowledgeTypeId = arguments?.getInt(AppConstants.BundleKey.KNOWLEDGE_TYPE_ID, 0)!!

        iniAdapter()

        viewModel.questype.set(specialType)
        viewModel.tvTitle.set(knowledgeType)
        viewModel.knowledgeId.set(knowledgeId)
        viewModel.knowledgeIdType.set(knowledgeTypeId)

        viewModel.getPracticeTestDetail()

    }

    override fun initViewObservable() {
        viewModel.uc.onPracticeTestDetailEvent.observe(this, {
            binding.datas = it
            if (it.questionDe.question.collected == "0") {
                viewModel.isCorrect.set(0)
                viewModel.ivToolbarIconRes.set(R.mipmap.icon_tk_collect_n)
            } else {
                viewModel.isCorrect.set(1)
                viewModel.ivToolbarIconRes.set(R.mipmap.icon_tk_collect_y)
            }
            binding.tvTitle.text = knowledgeName
            kind = it.questionDe.question.kind.toInt()
            viewModel.kind.set(kind)

            if (knowledgeTypeId == 1) {
                binding.cardView.visibility = View.VISIBLE
                binding.tvOk.visibility = View.GONE
            } else {
                binding.cardView.visibility = View.GONE
                binding.tvOk.visibility = View.GONE
            }

            practiceItemAdapter.setShow(false, knowledgeTypeId)
            practiceItemAdapter.setKind(kind)

            it.apply {
                when (kind) {
                    1, 2, 3 -> {
                        if (kind == 1) {
                            binding.tvQuestionType.text = "【单选题】"
                        }
                        if (kind == 3) {
                            binding.tvQuestionType.text = "【多选题】"
                            binding.tvOk.visibility = View.VISIBLE
                        }
                        if (kind == 2) {
                            binding.tvQuestionType.text = "【判断题】"
                        }
                        binding.rvList.visibility = View.VISIBLE
                        binding.etInput.visibility = View.GONE

                        if (questionDe.quOptions.isNotEmpty()) {
                            practiceItemAdapter.data.clear()
                            practiceItemAdapter.setList(questionDe.quOptions)

                            if (knowledgeTypeId == 1) {//背题模式
                                practiceItemAdapter.setShow(true, knowledgeTypeId)
                                binding.tvOk.visibility = View.GONE
                                practiceItemAdapter.notifyDataSetChanged()
                            } else {
                                if (questionDe.question.myAnswer.isNotEmpty()) {
                                    practiceItemAdapter.setAnswer(questionDe.question.myAnswer)
                                    practiceItemAdapter.setShow(true, knowledgeTypeId)
                                    binding.tvOk.visibility = View.GONE
                                    practiceItemAdapter.notifyDataSetChanged()
                                }
                            }

                        } else {
                            val emptyView = View.inflate(
                                context,
                                com.czl.lib_base.R.layout.common_empty_layout,
                                null
                            )
                            emptyView.findViewById<ViewGroup>(com.czl.lib_base.R.id.ll_empty)
                                .setOnClickListener {
                                }
                            practiceItemAdapter.setEmptyView(emptyView)
                        }
                    }

                    else -> {
                        if (kind == 4) {
                            binding.tvQuestionType.text = "【填空题】"
                        }
                        if (kind == 5) {
                            binding.tvQuestionType.text = "【简答题】"
                        }
                        if (kind == 6) {
                            binding.tvQuestionType.text = "【论述题】"
                        }
                        binding.rvList.visibility = View.GONE
                        binding.etInput.visibility = View.VISIBLE
                    }
                }

                if (ids != null) {
                    if (ids.isNotEmpty()) {
                        specialSelectTypeBeans.clear()
                        for (id: SpecialDetailBean.Id in ids) {
                            val specialSelectTypeBean = SpecialSelectTypeBean(id.id, "-1")
                            specialSelectTypeBeans.add(specialSelectTypeBean)
                        }
                    }
                }

                binding.tvNum.text = "${nowQuestionNum + 1}/${specialSelectTypeBeans.size}"

                viewModel.questid.set(questionDe.question.id)//当前题目id
                viewModel.exerid.set(exerid)

            }

        })

        //答题卡
        viewModel.uc.onAnswerSheetEvent.observe(this, {
            showAnswerSheetDialog()
        })

        //上一题
        viewModel.uc.onPreviousQuestionEvent.observe(this, {

            viewModel.myanswer.set("")
            practiceItemAdapter.setAnswer("")

            nowQuestionNum--
            if (nowQuestionNum < 0) {
                ToastHelper.showErrorToast("已经是第一题了！")
                return@observe
            }
            viewModel.questionId.set(specialSelectTypeBeans[nowQuestionNum].id)
            viewModel.getPracticeTestDetail()
        })

        //下一题
        viewModel.uc.onNextQuestionEvent.observe(this, {

            viewModel.myanswer.set("")
            practiceItemAdapter.setAnswer("")

            if (getUserAnswer().isNotEmpty()) {
                specialSelectTypeBeans[nowQuestionNum].answerType = "1"
            }

            nowQuestionNum++

            if (nowQuestionNum > specialSelectTypeBeans.size - 1) {
                ToastHelper.showErrorToast("已经是最后一题了！")
                return@observe
            }
            viewModel.questionId.set(specialSelectTypeBeans[nowQuestionNum].id)
            viewModel.getPracticeTestDetail()
        })

        //题目分析
        viewModel.uc.onTopicAnalysisEvent.observe(this, {
            if (binding.cardView.visibility == View.VISIBLE) {
                binding.cardView.visibility = View.GONE
            } else {
                binding.cardView.visibility = View.VISIBLE
            }
        })

        //多选题确定按钮
        viewModel.uc.onMultipleChoiceToConfirmEvent.observe(this, {
            practiceItemAdapter.setShow(true, knowledgeTypeId)
            practiceItemAdapter.notifyDataSetChanged()
        })

        //答题卡点击返回数据
        LiveBusCenter.observePracticeIdPositionEvent(this, {
            if (getUserAnswer().isNotEmpty()) {
                specialSelectTypeBeans[nowQuestionNum].answerType = "1"
            }

            viewModel.questionId.set(it.id)
            nowQuestionNum = it.pos

            viewModel.questionId.set(specialSelectTypeBeans[nowQuestionNum].id)
            viewModel.getPracticeTestDetail()
        })

    }


    private fun showAnswerSheetDialog() {
        XPopup.Builder(AppManager.instance.currentActivity())
            .isDarkTheme(false)
            .hasShadowBg(true)
            .asCustom(
                AnswerSheetPopView(
                    AppManager.instance.currentActivity() as BaseActivity<*, *>,
                    specialSelectTypeBeans
                )
            )
            .show()
    }

    private fun getUserAnswer(): String {
        var answer = ""
        if (kind in 1..3) {
            for (quoption: SpecialDetailBean.QuOption in practiceItemAdapter.data) {
                if (quoption.select) {
                    answer = "${answer}${quoption.type},"
                }
            }
            if (answer.isNotEmpty()) {
                answer = answer.substring(0, answer.length - 1)
            }
        } else {
            answer = binding.etInput.text.toString()
        }
        viewModel.myanswer.set(answer)
        if (answer.isEmpty()) {
            return ""
        }
        return answer
    }

    fun iniAdapter() {
        practiceItemAdapter = PracticeItemAdapter(this)
        practiceItemAdapter.setDiffCallback(practiceItemAdapter.diffUtil)
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = practiceItemAdapter
        }
    }

}