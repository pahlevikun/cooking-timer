package id.pahlevikun.cookingtimer.common.extension

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import id.pahlevikun.cookingtimer.R
import id.pahlevikun.cookingtimer.common.helper.Helper

fun Context.getToolbarHeight() =
    resources.getDimension(com.google.android.material.R.dimen.abc_action_bar_default_height_material).toInt()

fun Context.getToolbarAndStatusBarHeight(): Int {
    return getToolbarHeight() + getStatusBarHeight()
}

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }

    return result
}

fun Context.countDisplaySize(
    halfSize: Int = 2,
    divider: Int = 10,
    isUseToolbarSize: Boolean = false
): Int {
    val tv = TypedValue()
    var actionBarHeight = divider
    if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        if (isUseToolbarSize)
            actionBarHeight =
                TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    }
    return this.resources.displayMetrics.heightPixels / halfSize - actionBarHeight
}

fun Context.countDisplayHeight(): Int {
    return this.resources.displayMetrics.heightPixels
}

fun Context.countDisplayWidth(): Int {
    return this.resources.displayMetrics.widthPixels
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.isNetworkAvailable(): Boolean {
    val activeNetworkInfo =
        getSysService<ConnectivityManager>(Context.CONNECTIVITY_SERVICE)?.activeNetworkInfo
    return activeNetworkInfo?.isConnected ?: false
}

inline fun <reified T> Context.getSysService(systemService: String): T? {
    return getSystemService(systemService) as T
}

fun Context.showKeyboard() {
    val inputMethodManager = getSysService<InputMethodManager>(Context.INPUT_METHOD_SERVICE)
    inputMethodManager?.toggleSoftInput(
        InputMethodManager.SHOW_IMPLICIT,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

fun Context.hideKeyboard(view: View) {
    val inputManager = getSysService<InputMethodManager>(Context.INPUT_METHOD_SERVICE)
    inputManager?.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Context.forceHideKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Context.getScreenSize(): IntArray {
    val widthHeight = IntArray(2)
    widthHeight[Helper.WIDTH_INDEX] = 0
    widthHeight[Helper.HEIGHT_INDEX] = 0

    val windowManager = this.getSysService<WindowManager>(Context.WINDOW_SERVICE)
    val display = windowManager?.defaultDisplay

    val size = Point()
    display?.getSize(size)
    widthHeight[Helper.WIDTH_INDEX] = size.x
    widthHeight[Helper.HEIGHT_INDEX] = size.y

    if (!Helper.isScreenSizeRetrieved(widthHeight)) {
        val metrics = DisplayMetrics()
        display?.getMetrics(metrics)
        widthHeight[0] = metrics.widthPixels
        widthHeight[1] = metrics.heightPixels
    }

    // Last defense. Use deprecated API that was introduced in lower than API 13
    if (!Helper.isScreenSizeRetrieved(widthHeight)) {
        widthHeight[0] = display?.width ?: 0 // deprecated
        widthHeight[1] = display?.height ?: 0 // deprecated
    }

    return widthHeight
}

fun Context.getToolBarHeight(): Int {
    val attrs = intArrayOf(R.attr.actionBarSize)
    val ta = this.obtainStyledAttributes(attrs)
    val toolBarHeight = ta.getDimensionPixelSize(0, -1)
    ta.recycle()

    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }

    return toolBarHeight + result
}

fun Context.isConnectedToInternet(): Boolean {
    return try {
        val p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com")
        val returnVal = p1.waitFor()
        (returnVal == 0)
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Context.getStringColor(@ColorRes color: Int): String {
    return "#" + Integer.toHexString(
        ContextCompat.getColor(
            this,
            color
        )
    )
}