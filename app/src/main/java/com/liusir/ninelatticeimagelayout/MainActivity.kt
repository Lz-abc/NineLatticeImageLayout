package com.liusir.ninelatticeimagelayout

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liusir.ninelatticeimagelayout.base.BaseActivity
import com.liusir.ninelatticeimagelayout.ui.adapter.MainListAdapter
import com.lz_abc.ninelatticelayout.bean.BaseData
import com.lz_abc.ninelatticelayout.utils.NineLatticeLayoutConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        var lm = LinearLayoutManager(this)
        lm.orientation = VERTICAL
        rvList.layoutManager = lm
var url="http://d.ifengimg.com/w600/p0.ifengimg.com/pmop/2018/0829/81A01549F726A277DC480C8916232811ADB45D1B_size150_w1080_h2160.jpeg"
//       var url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582633416778&di=77af8497bad7da8761546bfb3517e2b0&imgtype=0&src=http%3A%2F%2Fimg.ewebweb.com%2Fuploads%2F20190403%2F15%2F1554275984-CghfBeMolO.jpg"
        val list = ArrayList<MainListAdapter.Item>()
        for (i in 1..10) {
            val itemArray = ArrayList<String>()
            itemArray.add(url)
            val item1 = MainListAdapter.Item()
            item1.list = itemArray
            list.add(item1)

            val itemArray2 = ArrayList<String>()
            for (index in 0..2){
                itemArray2.add(url)
            }
            val item2 = MainListAdapter.Item()
            item2.list = itemArray2
            list.add(item2)

            val itemArray3 = ArrayList<String>()
            for (index in 0..8){
                itemArray3.add(url)
            }
            val item3 = MainListAdapter.Item()
            item3.list = itemArray3
            list.add(item3)
        }
        rvList.adapter = MainListAdapter(list)

        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (abs(dy) > 0) {
                    Glide.with(this@MainActivity).pauseRequests()
                } else {
                    Glide.with(this@MainActivity).resumeRequests()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, state: Int) {
                //快速滑动突然停止 dy仍然会保留之前数值 所以还需要判断是否停止滑动
                when (state) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        //当屏幕停止滚动，加载图片
                        Glide.with(this@MainActivity).resumeRequests()
                    }
                }
            }
        })
        loadLayout.showContent()
    }
}
