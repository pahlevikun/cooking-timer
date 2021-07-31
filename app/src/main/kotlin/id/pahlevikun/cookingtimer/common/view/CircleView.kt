package id.pahlevikun.cookingtimer.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import id.pahlevikun.cookingtimer.R

class CircleView : View {

    companion object {
        private const val DEFAULT_DIMENSION = 4f
    }

    private var radius: Float =
        DEFAULT_DIMENSION
    private var color: Int = Color.WHITE
    private var paint: Paint? = null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        paint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleView)
        radius = a.getDimension(
            R.styleable.CircleView_radius,
            DEFAULT_DIMENSION
        )
        color = a.getColor(R.styleable.CircleView_color, Color.WHITE)

        a.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        paint?.color = color
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint!!)
        super.onDraw(canvas)
    }

    fun setRadius(radius: Float) {
        this.radius = radius
        postInvalidate()
    }

    fun setColor(color: Int) {
        this.color = color
        postInvalidate()
    }
}