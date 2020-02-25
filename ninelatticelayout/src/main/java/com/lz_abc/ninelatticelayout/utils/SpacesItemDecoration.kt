package com.lz_abc.ninelatticelayout.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lz_abc.ninelatticelayout.bean.DecorationState

class SpacesItemDecoration : RecyclerView.ItemDecoration {

    private var left: Int = 0
    private var right: Int = 0
    private var top: Int = 0
    private var bottom: Int = 0
    private var space: Int = 0

    private var state: DecorationState = DecorationState.LINE_V

    constructor(left: Int, right: Int, top: Int, bottom: Int) : super() {
        this.left = left
        this.right = right
        this.top = top
        this.bottom = bottom
    }

    constructor(space: Int, state: DecorationState) : super() {
        this.space = space
        this.state = state
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val index: Int = parent.getChildAdapterPosition(view)
        outRect.setEmpty()
        when (this.state) {
            DecorationState.LINE_V -> {
                outRect.left = 0
                outRect.right = 0
                outRect.top = 0
                outRect.bottom = space
            }
            DecorationState.LINE_H -> {
                if (index == 0) {
                    outRect.left = space
                } else {
                    outRect.left = 0
                }
                outRect.right = space
                outRect.top = 0
                outRect.bottom = 0
            }
            DecorationState.GRID -> {
                if (parent.layoutManager is GridLayoutManager) {
                    var manager: GridLayoutManager = parent.layoutManager as GridLayoutManager
                    var sc: Int = manager.spanCount
                    var dh = space
                    var lrs = space
                    var m = index % sc
                    var v = index / sc

                    //处理中间分割线问题[原始间隔]
                    if (m != sc - 1) {
                        //y = -x + (sc - 1)
                        outRect.right = (((sc - 1 - m) * 1f * dh / sc).toInt())
                    }

                    if (m != 0) {
                        //y = x
                        outRect.left = (((m) * 1.0f * dh / sc).toInt())
                    }

                    //处理两端间距分配给每个item问题
                    if (m != sc - 1) {
                        //y = 2x - (sc - 2)
                        outRect.right += ((2 * m - (sc - 2)) * 1.0f * lrs / sc).toInt()
                    }

                    if (m != 0) {
                        //y = -2x + sc
                        outRect.left += ((-2 * m + sc) * 1.0f * lrs / sc).toInt()
                    }


                    //处理两端设置的间距问题
                    if (m == sc - 1) {
//                        outRect.right = lrs
//                        outRect.right = 0
                    }

                    if (m == 0) {
//                        outRect.left = lrs
                        outRect.left = 0
                    }


                    if (v != 0) {
                        outRect.top = space
                    }
//                    outRect.bottom = space
                }
            }
        }
    }
}