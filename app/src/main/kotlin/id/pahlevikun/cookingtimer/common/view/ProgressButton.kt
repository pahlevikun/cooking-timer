package id.pahlevikun.cookingtimer.common.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatButton
import id.pahlevikun.cookingtimer.R

class ProgressButton : AppCompatButton {
    var isLoading: Boolean? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(attrs)
    }

    private var buttonText: String? = null

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.DefaultProgressBar, 0, 0)
        try {
            isLoading = typedArray.getBoolean(R.styleable.DefaultProgressBar_loading, false)
            buttonText = this.text.toString()
            if (isLoading == true) {
                enableLoadingState()
            }
        } finally {
            typedArray.recycle()
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isLoading == true) {
            View.mergeDrawableStates(drawableState, intArrayOf(R.attr.loading))
        }
        return drawableState
    }

    fun enableLoadingState() {
        this.text = ""
        this.isLoading = true
        this.isClickable = false
        this.refreshDrawableState()
        this.showProgress()
    }

    private fun showProgress() {
        var background = this.background
        if (background == null) {
            TypeCastException("null cannot be cast to non-null type android.graphics.drawable.StateListDrawable")
        } else {
            background = background.current
            if (background == null) {
                throw TypeCastException("null cannot be cast to non-null type android.graphics.drawable.LayerDrawable")
            } else {
                val loader = (background as LayerDrawable).getDrawable(1)
                val animator = ObjectAnimator.ofInt(loader, "level", 0, 10000)
                animator.duration = 700L
                animator.interpolator = DecelerateInterpolator()
                animator.repeatMode = ValueAnimator.RESTART
                animator.repeatCount = ValueAnimator.INFINITE
                animator.start()
            }
        }
    }

    fun disableLoadingState() {
        this.text = buttonText
        this.isLoading = false
        this.isClickable = true
        this.refreshDrawableState()
    }

    fun setButtonEnable(enabled: Boolean) {
        this.isEnabled = enabled
    }

    fun setButtonColor(drawable: Drawable) {
        this.background = drawable
    }

    fun setButtonTextColor(color: Int) {
        this.setTextColor(color)
    }

    fun setButtonIconLeft(drawable: Drawable?) {
        this.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    fun setButtonText(text: String) {
        this.buttonText = text
        this.text = text
    }
}