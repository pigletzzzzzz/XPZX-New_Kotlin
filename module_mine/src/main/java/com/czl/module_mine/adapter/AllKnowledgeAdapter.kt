package com.czl.module_mine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.czl.lib_base.data.bean.QuestionDataBean
import com.czl.module_mine.R

class AllKnowledgeAdapter : BaseExpandableListAdapter {

    var allKnowledgeDetailFirst: MutableList<QuestionDataBean.SubKp> = mutableListOf()
    var allKnowledgeDetailSecond: MutableList<MutableList<QuestionDataBean.SubKp>> = mutableListOf()
    lateinit var groupViewHolder: GroupViewHolder
    lateinit var childViewHolder: ChildViewHolder

    constructor(
        allKnowledgeDetailFirst: MutableList<QuestionDataBean.SubKp>,
        allKnowledgeDetailSecond: MutableList<MutableList<QuestionDataBean.SubKp>>
    ) : super(){
        this.allKnowledgeDetailFirst = allKnowledgeDetailFirst
        this.allKnowledgeDetailSecond = allKnowledgeDetailSecond
    }

    //返回第一级List长度
    override fun getGroupCount(): Int {
        return allKnowledgeDetailFirst.size
    }

    //返回指定groupPosition的第二级List长度
    override fun getChildrenCount(groupPosition: Int): Int {
        return allKnowledgeDetailSecond[groupPosition].size
    }

    //返回一级List里的内容
    override fun getGroup(groupPosition: Int): Any {
        return allKnowledgeDetailFirst[groupPosition]
    }

    //返回二级List的内容
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return allKnowledgeDetailSecond[groupPosition][childPosition]
    }

    //返回一级View的id 保证id唯一
    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    //返回二级View的id 保证id唯一
    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return (groupPosition+childPosition).toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var mconvertView = convertView
        if (convertView == null){
            mconvertView =  LayoutInflater.from(parent?.context).inflate(R.layout.adapter_knowledge_one, parent,false)
            groupViewHolder = GroupViewHolder(mconvertView)
            mconvertView?.tag = groupViewHolder
        }else{
            groupViewHolder = mconvertView?.tag as GroupViewHolder
        }
        groupViewHolder.tv_title_one?.text = allKnowledgeDetailFirst[groupPosition].title

        return mconvertView!!
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var mconvertView = convertView
        if (convertView == null){
            mconvertView =  LayoutInflater.from(parent?.context).inflate(R.layout.adapter_knowledge_two, parent,false)
            childViewHolder = ChildViewHolder(mconvertView)
            // 用mconvertView代替convertView，不然会有空指针异常
            mconvertView?.tag = childViewHolder
        }else{
            childViewHolder = mconvertView?.tag as ChildViewHolder
        }
        childViewHolder.tv_title_two?.text = allKnowledgeDetailSecond[groupPosition][childPosition].title

        return mconvertView!!
    }

    //指定位置的子项是否可选
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    inner class GroupViewHolder(view: View) {
        var tv_title_one: TextView? = null

        init {
            tv_title_one = view.findViewById(R.id.tv_title_one)
        }
    }

    inner class ChildViewHolder(view: View) {
        var tv_title_two: TextView? = null

        init {
            tv_title_two = view.findViewById(R.id.tv_title_two)
        }
    }
}