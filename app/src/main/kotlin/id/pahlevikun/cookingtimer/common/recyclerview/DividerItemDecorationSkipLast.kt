package id.pahlevikun.cookingtimer.common.recyclerview

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecorationSkipLast(context: Context, resId: Int) :
    DividerItemDecoration(context, resId) {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider!!.intrinsicHeight

            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(c)
        }
    }
}