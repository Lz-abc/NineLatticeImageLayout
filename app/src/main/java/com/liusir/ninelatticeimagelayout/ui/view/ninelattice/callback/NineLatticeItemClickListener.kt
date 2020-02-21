package com.liusir.ninelatticeimagelayout.ui.view.ninelattice.callback

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

interface NineLatticeItemClickListener {
    fun onItemClick(adapter: BaseQuickAdapter<*,BaseViewHolder>, view: View, position:Int)
}