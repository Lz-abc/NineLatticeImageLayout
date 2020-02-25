package com.lz_abc.ninelatticelayout

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lz_abc.ninelatticelayout.adapter.ItemAdapter
import com.lz_abc.ninelatticelayout.bean.BaseData
import com.lz_abc.ninelatticelayout.bean.DecorationState
import com.lz_abc.ninelatticelayout.callback.ImageCallback
import com.lz_abc.ninelatticelayout.callback.NineLatticeItemChildClickListener
import com.lz_abc.ninelatticelayout.callback.NineLatticeItemClickListener
import com.lz_abc.ninelatticelayout.utils.NineLatticeLayoutConfig
import com.lz_abc.ninelatticelayout.utils.SpacesItemDecoration

class NineLatticeImageLayout : RecyclerView {
    private var spanCount: Int = 1//网格列数
    private var space: Int = 10//间隔大小
    private var decorationState = DecorationState.GRID//间隔模式 默认网格
    private var singleImgSize: Int = 0//单张图片大小
    private var gridSize: Int = 0//网格图片大小
    private var data: List<String>? = null//图片列表
    private var defaultDarw:Int=0//默认图片
    private var errorDraw:Int=0//错误图片
    private var blankDraw:Int=0//空图片
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context, attrs: AttributeSet?, def: Int) : super(context, attrs, def) {
        val attr = context.obtainStyledAttributes(attrs!!, R.styleable.NineLatticeLayout, def, 0)
        attr.let {
            spanCount = attr.getInt(R.styleable.NineLatticeLayout_grid_count, spanCount)
            space = attr.getDimensionPixelSize(R.styleable.NineLatticeLayout_space, space)
            singleImgSize=attr.getDimensionPixelSize(R.styleable.NineLatticeLayout_single_img_size,0)
            defaultDarw=attr.getResourceId(R.styleable.NineLatticeLayout_default_drawable,0)
            errorDraw=attr.getResourceId(R.styleable.NineLatticeLayout_error_drawable,0)
            blankDraw=attr.getResourceId(R.styleable.NineLatticeLayout_blank_drawable,0)
        }
        NineLatticeLayoutConfig.init(context)
        init()
    }

    private fun init() {
        layoutManager = GridLayoutManager(context, spanCount)
        this.adapter = itemAdapter
        addItemDecoration(
            SpacesItemDecoration(
                space,
                decorationState
            )
        )
    }


    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        val width = MeasureSpec.getSize(widthSpec)
        var height = MeasureSpec.getSize(heightSpec)
        val totalWidth = width - paddingLeft - paddingRight
        data?.let {
            if (it.isNotEmpty()) {
                gridSize = if (it.size == 1) {
                    if (singleImgSize > totalWidth) totalWidth else singleImgSize
                } else {
                    (totalWidth - ((spanCount - 2) * space)) / spanCount
                }
                itemAdapter.setHeight(gridSize)
                itemAdapter.notifyDataSetChanged()
                val rowCount=(it.size / spanCount + if (it.size % spanCount > 0) 1 else 0)
                val w=(rowCount-1)*space
                height = (gridSize * rowCount)+ (w + paddingTop + paddingBottom)
            }
        }
        setMeasuredDimension(totalWidth, height)
    }

    /**
     * 设置数据 用于计算调整宽高
     */
    fun setData(data: List<String>) {
        this.data = data
        layoutManager = if (data.size == 1) {
            GridLayoutManager(context,1)
        } else {
            GridLayoutManager(context, spanCount)
        }
        requestFocus()
        itemAdapter.setNewData(data)
    }

    fun setOnClickListener(listener: NineLatticeItemClickListener){
        itemAdapter.setOnItemClickListener { adapter, view, position ->
            listener.onItemClick(adapter,view,position)
        }
    }

    fun setOnItemChildClickListener(listener: NineLatticeItemChildClickListener){
        itemAdapter.setOnItemChildClickListener { adapter, view, position ->
            listener.onItemChildClick(adapter,view,position)
        }
    }

    private val itemAdapter: ItemAdapter<String> by lazy {
        ItemAdapter(listOf(),
            object : ImageCallback<String> {
                override fun loadImage(data: String?, image: ImageView) {
                    data?.let {
                        Glide.with(context).load(data)
                            .error(errorDraw)//加载错误
                            .placeholder(defaultDarw)//默认
                            .fallback(blankDraw)//路径为空
                            .into(image)
                    }
                }
            })
    }
}