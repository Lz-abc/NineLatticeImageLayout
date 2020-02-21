package com.liusir.ninelatticeimagelayout.ui.adapter

import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.liusir.ninelatticeimagelayout.R
import com.liusir.ninelatticeimagelayout.ui.view.ninelattice.NineLatticeImageLayout
import com.liusir.ninelatticeimagelayout.ui.view.ninelattice.bean.BaseData
import com.liusir.ninelatticeimagelayout.ui.view.ninelattice.callback.NineLatticeItemChildClickListener
import com.liusir.ninelatticeimagelayout.ui.view.ninelattice.callback.NineLatticeItemClickListener

class MainListAdapter(data: List<Item>) :
    BaseQuickAdapter<MainListAdapter.Item, BaseViewHolder>(R.layout.item_main, data) {
    class Item {
        var list: List<BaseData>? = null
    }

    override fun convert(helper: BaseViewHolder, item: Item?) {
        helper?.let {
            var nlLayout: NineLatticeImageLayout<BaseData> = it.getView(R.id.nlLayout)
            item?.list?.let { list ->
                nlLayout.setData(list)
            }
            nlLayout.setOnClickListener(object : NineLatticeItemClickListener {
                override fun onItemClick(
                    adapter: BaseQuickAdapter<*, BaseViewHolder>,
                    view: View,
                    position: Int
                ) {
                    Toast.makeText(mContext, "点击了$position", Toast.LENGTH_LONG).show()
                }
            })
            nlLayout.setOnItemChildClickListener(object : NineLatticeItemChildClickListener {
                override fun onItemChildClick(
                    adapter: BaseQuickAdapter<*, BaseViewHolder>,
                    view: View,
                    position: Int
                ) {
                    Toast.makeText(mContext, "点击了 Child $position", Toast.LENGTH_LONG).show()
                }

            })
        }
    }
}