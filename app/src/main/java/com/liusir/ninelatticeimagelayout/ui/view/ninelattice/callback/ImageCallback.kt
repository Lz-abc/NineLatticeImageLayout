package com.liusir.ninelatticeimagelayout.ui.view.ninelattice.callback

import android.widget.ImageView

interface ImageCallback<T> {
    fun loadImage(url:T?,image:ImageView)
}