package id.pahlevikun.cookingtimer.common.extension

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

fun Float.toPxFloat(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this,
        context.resources.displayMetrics
    )
}

fun Float.toPxInt(context: Context): Int {
    return toPxFloat(context).roundToInt()
}

fun Int.toPxFloat(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
        context.resources.displayMetrics
    )
}

internal fun Float.toDpFloat(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this,
        context.resources.displayMetrics
    )
}

internal fun Float.toDpInt(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this,
        context.resources.displayMetrics
    ).toInt()
}