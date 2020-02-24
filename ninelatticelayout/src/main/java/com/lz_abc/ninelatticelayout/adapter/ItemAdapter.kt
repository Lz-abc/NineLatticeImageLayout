package com.lz_abc.ninelatticelayout.adapter

import android.view.View.VISIBLE
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lz_abc.ninelatticelayout.callback.ImageCallback
import com.lz_abc.ninelatticelayout.R
import com.lz_abc.ninelatticelayout.utils.NineLatticeLayoutConfig

class ItemAdapter<T> : BaseQuickAdapter<T, BaseViewHolder> {

    private var imageCallback: ImageCallback<T>? = null

    private var gridSize: Int = 0

    constructor(data: List<T>, imageCallback: ImageCallback<T>) : super(
        NineLatticeLayoutConfig.getItemNiceLatticeLayoutId(),data
    ) {

        this.imageCallback = imageCallback
    }


    fun setHeight(gridSize: Int) {
        this.gridSize = gridSize
    }

    override fun convert(helper: BaseViewHolder, item: T?) {
        helper?.let {
            var image: ImageView = helper.getView(NineLatticeLayoutConfig.getItemNiceLatticeImageViewId())
            if (gridSize > 0) {
                image.layoutParams.height = gridSize
                image.layoutParams.width = gridSize
            }
            imageCallback?.let { inter ->
                image?.let { image ->
                    item?.let { bean ->
                        image.visibility = VISIBLE
                        inter.loadImage(bean, image)
                    }
                }
            }
        }
    }
}