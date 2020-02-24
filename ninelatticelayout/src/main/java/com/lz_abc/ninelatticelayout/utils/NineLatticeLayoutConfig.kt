package com.lz_abc.ninelatticelayout.utils

import android.content.Context


class NineLatticeLayoutConfig {

    companion object {
        private var niceLatticeLayoutId: Int = 0
        private var niceLatticeImageViewId:Int=0

        fun init(context: Context) {
            val packageName = context.applicationContext.packageName
            val resources = context.resources
            niceLatticeLayoutId =
                resources.getIdentifier("item_nine_lattice", "layout", packageName)
            niceLatticeImageViewId=
                resources.getIdentifier("iv_1","id",packageName)
        }

        fun getItemNiceLatticeLayoutId(): Int {
            return niceLatticeLayoutId
        }

        fun getItemNiceLatticeImageViewId():Int{
            return niceLatticeImageViewId
        }
    }


}