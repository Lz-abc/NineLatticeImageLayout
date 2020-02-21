package com.liusir.ninelatticeimagelayout.ui.view.ninelattice.adapter

import android.view.View.VISIBLE
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.liusir.ninelatticeimagelayout.R
import com.liusir.ninelatticeimagelayout.ui.view.ninelattice.callback.ImageCallback

class ItemAdapter<T> : BaseQuickAdapter<T, BaseViewHolder> {

    private var imageCallback: ImageCallback<T>? = null

    private var gridSize:Int=0

    constructor(data: List<T>, imageCallback: ImageCallback<T>) : super(
        R.layout.item_nine_lattice,data
    ) {
        this.imageCallback = imageCallback
    }


    fun setHeight(gridSize:Int){
        this.gridSize=gridSize
    }

    override fun convert(helper: BaseViewHolder, item: T?) {
        helper?.let {
            var image:ImageView= it.getView(R.id.iv_1)
            if (gridSize>0){
                image.layoutParams.height=gridSize
                image.layoutParams.width=gridSize
            }
            imageCallback?.let { inter ->
                image?.let { image ->
                    item?.let { bean ->
                        image.visibility= VISIBLE
                        inter.loadImage(bean, image)
                    }
                }
            }
        }
    }
}