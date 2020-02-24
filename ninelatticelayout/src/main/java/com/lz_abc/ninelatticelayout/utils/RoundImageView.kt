package com.lz_abc.ninelatticelayout.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView

import com.lz_abc.ninelatticelayout.R


/**
 * 自定义圆形图片或圆角图片
 * Created by ankang on 2017/12/15.
 */

class RoundImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {


    /**
     * 图片的类型，圆形or圆角
     */
    private var type: Int = 0
    /**
     * 圆角的大小
     */
    private var mBorderRadius: Int = 0

    /**
     * 绘图的Paint
     */
    private val mBitmapPaint: Paint = Paint()
    /**
     * 圆角的半径
     */
    private var mRadius: Int = 0
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private val mMatrix: Matrix = Matrix()
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private var mBitmapShader: BitmapShader? = null
    /**
     * view的宽度
     */
    private var mWidth: Int = 0
    private var mRoundRect: RectF? = null

    init {
        mBitmapPaint.isAntiAlias = true

        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundImageView
        )

        mBorderRadius = a.getDimensionPixelSize(
            R.styleable.RoundImageView_borderRadius, TypedValue
                .applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    BODER_RADIUS_DEFAULT.toFloat(), resources
                        .displayMetrics
                ).toInt()
        )// 默认为10dp
        type = a.getInt(R.styleable.RoundImageView_lltype, TYPE_CIRCLE)// 默认为Circle

        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //        Log.e("TAG", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(measuredWidth, measuredHeight)
            mRadius = mWidth / 2
            setMeasuredDimension(mWidth, mWidth)
        }

    }


    /**
     * 初始化BitmapShader
     */
    private fun setUpShader() {
        val drawable = drawable ?: return

        val bmp = drawableToBitamp(drawable)
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        var scale = 1.0f
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            val bSize = Math.min(bmp.width, bmp.height)
            scale = mWidth * 1.0f / bSize

        } else if (type == TYPE_ROUND) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(width * 1.0f / bmp.width, height * 1.0f / bmp.height)
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale)
        // 设置变换矩阵
        mBitmapShader!!.setLocalMatrix(mMatrix)
        // 设置shader
        mBitmapPaint.shader = mBitmapShader
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private fun drawableToBitamp(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null) {
            return
        }
        setUpShader()

        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(
                mRoundRect!!, mBorderRadius.toFloat(), mBorderRadius.toFloat(),
                mBitmapPaint
            )
        } else {
            canvas.drawCircle(mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), mBitmapPaint)
            // drawSomeThing(canvas);
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 圆角图片的范围
        if (type == TYPE_ROUND)
            mRoundRect = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState())
        bundle.putInt(STATE_TYPE, type)
        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            super.onRestoreInstanceState(
                state
                    .getParcelable(STATE_INSTANCE)
            )
            this.type = state.getInt(STATE_TYPE)
            this.mBorderRadius = state.getInt(STATE_BORDER_RADIUS)
        } else {
            super.onRestoreInstanceState(state)
        }

    }

    fun setBorderRadius(borderRadius: Int) {
        val pxVal = dp2px(borderRadius)
        if (this.mBorderRadius != pxVal) {
            this.mBorderRadius = pxVal
            invalidate()
        }
    }

    fun setType(type: Int) {
        if (this.type != type) {
            this.type = type
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
                this.type = TYPE_CIRCLE
            }
            requestLayout()
        }

    }

    fun dp2px(dpVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal.toFloat(), resources.displayMetrics
        ).toInt()
    }

    /**
     * 绘制形状,在bitmap画圆角矩形跟圆形
     *
     * @return
     */
    fun createBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(
            width, height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK

        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                mBorderRadius.toFloat(), mBorderRadius.toFloat(), paint
            )
        } else {
            canvas.drawCircle(
                (width / 2).toFloat(), (width / 2).toFloat(), (width / 2).toFloat(),
                paint
            )
        }

        return bitmap
    }


    fun setmBorderRadius(mBorderRadius: Int) {
        this.mBorderRadius = mBorderRadius
    }

    companion object {
        private val TYPE_CIRCLE = 0
        private val TYPE_ROUND = 1

        /**
         * 圆角大小的默认值
         */
        private val BODER_RADIUS_DEFAULT = 10

        private val STATE_INSTANCE = "state_instance"
        private val STATE_TYPE = "state_type"
        private val STATE_BORDER_RADIUS = "state_border_radius"
    }
}
