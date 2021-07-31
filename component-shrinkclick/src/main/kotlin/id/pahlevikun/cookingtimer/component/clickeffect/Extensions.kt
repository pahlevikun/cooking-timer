package id.pahlevikun.cookingtimer.component.clickeffect

import android.os.Handler
import android.view.View

fun View.applyClickShrink(): View {
    return this.apply {
        ClickShrinkEffect(this)
    }
}

fun View.setOnSingleClickListener(
    debounceTime: Long = 400L,
    action: ((view: View) -> Unit)? = null
) {
    this.applyClickShrink()
        .setOnClickListener(object : View.OnClickListener {
            private var allowClick: Boolean = true

            override fun onClick(v: View) {
                if (allowClick && action != null) {
                    action.invoke(v)
                    Handler().postDelayed({ allowClick = true }, debounceTime)
                }
            }
        })
}