package com.czl.module_mine.fragment

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.QuestionDataBean
import com.czl.lib_base.data.bean.QuestionDataBean.SubKp
import com.czl.lib_base.event.LiveBusCenter
import com.czl.module_mine.BR
import com.czl.module_mine.R
import com.czl.module_mine.adapter.AllKnowledgeAdapter
import com.czl.module_mine.databinding.FragmentAllKnowledgeBinding
import com.czl.module_mine.viewmodel.AllKnowledgeViewModel

@Route(path = AppConstants.Router.Mine.F_ALL_KNOWLEDGE)
class AllKnowledgeFragment : BaseFragment<FragmentAllKnowledgeBinding, AllKnowledgeViewModel>() {

    private lateinit var allKnowledgeAdapter: AllKnowledgeAdapter
    private val allKnowledgeDetailFirst: MutableList<SubKp> = mutableListOf()
    private var allKnowledgeDetailSecond: MutableList<MutableList<SubKp>> = mutableListOf()

    override fun initContentView(): Int {
        return R.layout.fragment_all_knowledge
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return true
    }

    override fun initData() {
        super.initData()
        viewModel.tvTitle.set("知识点")

        viewModel.getKpsTreeData()

        allKnowledgeAdapter = AllKnowledgeAdapter(allKnowledgeDetailFirst, allKnowledgeDetailSecond)
        binding.elv.setAdapter(allKnowledgeAdapter)

    }

    override fun initViewObservable() {
        viewModel.uc.onQuestionDataEvent.observe(this, {
            it?.apply {
                if (top.isNotEmpty()) {
                    for (data: QuestionDataBean.Top in top) {
                        val subKpFirst = SubKp(data.id, data.title)
                        allKnowledgeDetailFirst.add(subKpFirst)

                        val subKp: MutableList<SubKp> = mutableListOf()
                        for (sub: SubKp in data.subKps) {
                            subKp.add(sub)
                        }
                        allKnowledgeDetailSecond.add(subKp)
                    }

                    allKnowledgeAdapter.notifyDataSetChanged()
                }
            }
        })

        binding.elv.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Log.d("知识点选择--->", allKnowledgeDetailSecond[groupPosition][childPosition].title)
            LiveBusCenter.postKnowledgeSelectSuccessEvent(
                allKnowledgeDetailSecond[groupPosition][childPosition].title,
                allKnowledgeDetailSecond[groupPosition][childPosition].id
            )
            viewModel.finish()
            false
        }
    }

}