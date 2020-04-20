package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

/**
 * src 和 dest 都不是 bitmap
 *
 */
class XferModeNoBitmapView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var mode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    var srcRect: RectF = RectF(0f, 70f, 100f, 100f)

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val saveCount =
                it.saveLayer(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), null)

            paint.color = Color.BLUE
            paint.style = Paint.Style.FILL
            it.drawCircle(
                50f, 50f,
                50f, paint
            )  // dst

            paint.color = Color.RED


            paint.xfermode = mode
            canvas.drawRect(srcRect, paint)

            paint.xfermode = null
            it.restoreToCount(saveCount)
        }

    }
}

/**
 * src 是bitmap
 */
class XferModeView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var mode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    var srcRect: RectF = RectF(0f, 70f, 100f, 100f)
    val bitmapCanvas = Canvas()
    var bitmap: Bitmap? = null

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val saveCount = it.saveLayer(
                0f,
                0f,
                canvas.width.toFloat(),
                canvas.height.toFloat(),
                null,
                Canvas.ALL_SAVE_FLAG
            )

            paint.color = Color.BLUE
            paint.style = Paint.Style.FILL
            it.drawCircle(
                50f, 50f,
                50f, paint
            )  // dst

            paint.color = Color.RED

            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            }

            bitmapCanvas.setBitmap(bitmap)
            bitmapCanvas.drawRect(srcRect, paint)
            paint.xfermode = mode
            canvas.drawBitmap(bitmap!!, 0f, 0f, paint)

            paint.xfermode = null
            it.restoreToCount(saveCount)

        }

    }
}

/**
 * src 和 dest 都有bitmap
 */
class XferModeView1 : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var mode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    var srcRect: RectF = RectF(0f, 70f, 100f, 100f)
    val bitmapCanvas = Canvas()
    var bitmap: Bitmap? = null

    var srcBitmap: Bitmap? = null
    var srcBitmapCanvas = Canvas()

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val saveCount = it.saveLayer(
                0f,
                0f,
                canvas.width.toFloat(),
                canvas.height.toFloat(),
                null,
                Canvas.ALL_SAVE_FLAG
            )

            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            }
            if (srcBitmap == null) {
                srcBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            }

            paint.color = Color.BLUE
            paint.style = Paint.Style.FILL

            bitmapCanvas.setBitmap(bitmap)
            bitmapCanvas.drawCircle(50f, 50f,
                50f, paint)  // dst
            canvas.drawBitmap(bitmap!!, 0f, 0f ,paint)

            paint.color = Color.RED
            srcBitmapCanvas.setBitmap(srcBitmap)
            srcBitmapCanvas.drawRect(srcRect, paint)
            paint.xfermode = mode

            canvas.drawBitmap(srcBitmap!!, 0f, 0f, paint)

            paint.xfermode = null
            it.restoreToCount(saveCount)
        }

    }
}