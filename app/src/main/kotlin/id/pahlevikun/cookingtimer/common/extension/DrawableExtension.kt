package id.pahlevikun.cookingtimer.common.extension

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import id.pahlevikun.cookingtimer.R

fun Drawable.filter(context: Context) {
    val color = ContextCompat.getColor(context, R.color.green_80)
    this.setColorFilter(color, PorterDuff.Mode.SRC_IN)
}