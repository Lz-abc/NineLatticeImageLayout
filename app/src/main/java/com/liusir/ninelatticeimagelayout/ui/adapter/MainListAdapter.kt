package com.liusir.ninelatticeimagelayout.ui.adapter

import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.liusir.ninelatticeimagelayout.R
import com.lz_abc.ninelatticelayout.NineLatticeImageLayout
import com.lz_abc.ninelatticelayout.bean.BaseData
import com.lz_abc.ninelatticelayout.callback.NineLatticeItemChildClickListener
import com.lz_abc.ninelatticelayout.callback.NineLatticeItemClickListener

class MainListAdapter(data: List<Item>) :
    BaseQuickAdapter<MainListAdapter.Item, BaseViewHolder>(R.layout.item_main, data) {
    class Item {
        var list: List<String>? = null
    }

    override fun convert(helper: BaseViewHolder, item: Item?) {
        helper?.let {
            var nlLayout: NineLatticeImageLayout = it.getView(R.id.nlLayout)
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