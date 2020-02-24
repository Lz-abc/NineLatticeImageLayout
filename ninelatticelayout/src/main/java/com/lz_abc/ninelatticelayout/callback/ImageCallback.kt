package com.lz_abc.ninelatticelayout.callback

import android.widget.ImageView

interface ImageCallback<T> {
    fun loadImage(url:T?,image:ImageView)
}