package com.czl.lib_base.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**

 * 联系邮箱 pigletzz@outlook.com

 * 创建时间: 17:04

 * 描述:

 */
class GridSpaceItemDecoration : RecyclerView.ItemDecoration {
    private val TAG = "GridSpaceItemDecoration"

    private var mSpanCount = 0//横条目数量

    private var mRowSpacing = 0//行间距

    private var mColumnSpacing = 0// 列间距

    /**
     * @param spanCount     列数
     * @param rowSpacing    行间距
     * @param columnSpacing 列间距
     */
    constructor(spanCount: Int, rowSpacing: Int, columnSpacing: Int){
        mSpanCount = spanCount
        mRowSpacing = rowSpacing
        mColumnSpacing = columnSpacing
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // 获取view 在adapter中的位置。

        val column = position % mSpanCount // view 所在的列


        outRect.left = column * mColumnSpacing / mSpanCount // column * (列间距 * (1f / 列数))

        outRect.right =
            mColumnSpacing - (column + 1) * mColumnSpacing / mSpanCount // 列间距 - (column + 1) * (列间距 * (1f /列数))

        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing
        if (position >= mSpanCount) {
            outRect.top = mRowSpacing; // item top
        }
    }

}