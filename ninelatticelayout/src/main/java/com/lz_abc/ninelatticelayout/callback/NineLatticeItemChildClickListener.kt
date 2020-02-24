package com.lz_abc.ninelatticelayout.callback

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

interface NineLatticeItemChildClickListener {
    fun onItemChildClick(adapter: BaseQuickAdapter<*,BaseViewHolder>, view: View, position:Int)
}