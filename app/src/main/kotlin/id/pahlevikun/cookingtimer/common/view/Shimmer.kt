package id.pahlevikun.cookingtimer.common.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import id.pahlevikun.cookingtimer.R
import id.pahlevikun.cookingtimer.common.helper.Logger

class Shimmer : View {
    companion object {
        private const val ANIMATION_DURATION = 1500L
        private const val BG_COLOR = Color.WHITE
        private const val DEFAULT_VALUE = -1
    }

    private var animator: ValueAnimator? = null

    private var gradientPaint: Paint? = null
    private var gradientColors: IntArray? = null
    private val centerColor = ContextCompat.getColor(context, R.color.white)
    private val beforeEdgeColor = ContextCompat.getColor(context, R.color.black_05)
    private val edgeColor = ContextCompat.getColor(context, R.color.black_10)

    private var contentViewBitmap: Bitmap? = null
    private var contentViewPaint: Paint? = null
    private var contentViewRes: Int = -1
    private var contentView: View? = null
    private var gradientCenterColorWidth: Float = 0.1f
    private var bgColor = ContextCompat.getColor(context, R.color.white)

    private val gradientColorDistribution: FloatArray
        get() {
            val colorDistribution = FloatArray(5)

            colorDistribution[0] = 0f
            colorDistribution[1] = 0.2f - gradientCenterColorWidth / 2f
            colorDistribution[2] = 0.4f + gradientCenterColorWidth / 2f
            colorDistribution[3] = 0.2f
            colorDistribution[4] = 1f

            return colorDistribution
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        initContentViewPaint()
        initGradientPaint()
        initAnimation()

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.Shimmer, 0, 0)
            contentViewRes = typedArray.getResourceId(R.styleable.Shimmer_layout, -1)
            if (contentViewRes == -1) {
                throw IllegalAccessException("cannot inflate shimmer view without a layout attribute")
            }
            typedArray.recycle()
        }
    }

    private fun initContentViewPaint() {
        contentViewPaint = Paint()
        contentViewPaint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
    }

    private fun initGradientPaint() {
        gradientPaint = Paint()
        gradientPaint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        gradientPaint?.isAntiAlias = true
        gradientColors =
            intArrayOf(edgeColor, beforeEdgeColor, centerColor, beforeEdgeColor, edgeColor)
    }

    private fun initAnimation() {
        animator = ValueAnimator.ofFloat(-1F, 2F)
        animator?.duration = ANIMATION_DURATION
        animator?.interpolator = LinearInterpolator()
        animator?.repeatCount = ValueAnimator.INFINITE

        animator?.addUpdateListener {
            updateGradient(width.toFloat(), it.animatedValue as Float)
            invalidate()
        }
    }

    private fun updateGradient(width: Float, animatedValue: Float) {
        val left = width * animatedValue
        val linearGradient = LinearGradient(
            left,
            0F,
            left + width,
            0F,
            gradientColors!!,
            gradientColorDistribution,
            Shader.TileMode.CLAMP
        )
        gradientPaint?.shader = linearGradient
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        if (contentView == null) {
            contentView = initContentView(
                widthMeasureSpec,
                measureDimension(desiredHeight, heightMeasureSpec)
            )
        }

        val contentWidth = contentView?.width ?: -1
        val contentHeight = contentView?.height ?: -1

        Logger.debug("Shimmer onMeasure width $contentWidth")
        Logger.debug("Shimmer onMeasure height $contentHeight")

        val wms = MeasureSpec.makeMeasureSpec(contentWidth, MeasureSpec.EXACTLY)
        val hms = MeasureSpec.makeMeasureSpec(contentHeight, MeasureSpec.EXACTLY)

        super.onMeasure(wms, hms)
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }

        if (result < desiredSize) {
            Logger.debug("The view is too small, the content might get cut")
        }
        return result
    }

    private fun initContentView(widthMeasureSpec: Int, heightMeasureSpec: Int): View {
        val inflater = LayoutInflater.from(context)
        val contentView = inflater.inflate(contentViewRes, parent as ViewGroup, false)
        contentView.measure(widthMeasureSpec, heightMeasureSpec)

        contentView.layout(0, 0, contentView.countWidth(), contentView.countHeight())
        contentView.background = null

        contentViewBitmap = try {
            Bitmap.createBitmap(
                contentView.countWidth(),
                contentView.countHeight(),
                Bitmap.Config.ALPHA_8
            )
        } catch (e: OutOfMemoryError) {
            Logger.debug("Shimmer initContentView \"Width = ${contentView.countWidth()}, Height = ${contentView.countHeight()}")
            System.gc()
            Bitmap.createBitmap(
                contentView.countWidth(),
                contentView.countHeight(),
                Bitmap.Config.ALPHA_8
            )
        }

        if (contentViewBitmap != null) {
            val canvas = Canvas(contentViewBitmap!!)
            contentView.draw(canvas)
        }

        return contentView
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (contentViewBitmap != null) {
            val width = contentViewBitmap?.width?.toFloat() ?: 0F
            val height = contentViewBitmap?.height?.toFloat() ?: 0F

            canvas?.drawBitmap(contentViewBitmap!!, 0F, 0F, contentViewPaint)
            canvas?.drawRect(0F, 0F, width, height, gradientPaint!!)
            canvas?.drawColor(BG_COLOR, PorterDuff.Mode.DST_ATOP)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateGradient(w.toFloat(), -1F)
        if (h <= 0 || w <= 0) {
            clearBitmap()
            animator?.cancel()
        }
    }

    @SuppressLint("SwitchIntDef")
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        when (visibility) {
            VISIBLE -> startAnimationIfNotAlreadyRunning()
            INVISIBLE, GONE -> animator?.cancel()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimationIfNotAlreadyRunning()
    }

    private fun startAnimationIfNotAlreadyRunning() {
        if (visibility == VISIBLE) {
            if (animator?.isRunning != true) {
                animator?.start()
            }
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
        clearBitmap()
    }

    private fun clearBitmap() {
        if (contentViewBitmap?.isRecycled == false) {
            contentViewBitmap?.recycle()
        }
        contentViewBitmap = null
    }

    private fun View?.countHeight(): Int {
        val height = this?.measuredHeight ?: DEFAULT_VALUE
        return if (height < 1) 1 else height
    }

    private fun View?.countWidth(): Int {
        val width = this?.measuredWidth ?: DEFAULT_VALUE
        return if (width < 1) 1 else width
    }
}