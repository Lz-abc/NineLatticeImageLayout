package com.liusir.ninelatticeimagelayout.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abc.baseloadview.views.SimpleBaseLoadView

abstract class BaseActivity :AppCompatActivity(),BaseInterface {

    val loadLayout:SimpleBaseLoadView by lazy{ SimpleBaseLoadView(this,getLayoutId()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loadLayout.layoutView)
        init()
    }

}