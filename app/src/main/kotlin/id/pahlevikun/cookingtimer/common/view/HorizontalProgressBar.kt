package id.pahlevikun.cookingtimer.common.view

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import id.pahlevikun.cookingtimer.R

@Suppress("DEPRECATION")
class HorizontalProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ProgressBar(context, attrs, android.R.attr.progressBarStyleHorizontal) {

    init {
        isIndeterminate = true
        indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(context, R.color.green_80),
            PorterDuff.Mode.SRC_ATOP
        )
    }
}