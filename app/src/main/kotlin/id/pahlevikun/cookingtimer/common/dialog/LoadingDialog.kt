package id.pahlevikun.cookingtimer.common.dialog

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.pahlevikun.cookingtimer.R
import id.pahlevikun.cookingtimer.common.extension.makeGone

class LoadingDialog(private val activity: Activity) {

    private val dialog: BottomSheetDialog = BottomSheetDialog(activity)
    private val contentView =
        activity.layoutInflater.inflate(R.layout.layout_dialog_loading, null)
    private val bottomSheetBehavior: BottomSheetBehavior<View>

    init {
        val dialogView = LayoutInflater
            .from(activity)
            .inflate(
                R.layout.layout_base_dialog_bottom,
                dialog.findViewById(android.R.id.content),
                false
            ).apply {
                findViewById<ViewGroup>(R.id.content_container).addView(contentView)
                findViewById<RelativeLayout>(R.id.rl_container_header).makeGone()
            }
        setCancelable()
        dialog.setContentView(dialogView)
        bottomSheetBehavior =
            BottomSheetBehavior.from(dialog.findViewById(R.id.design_bottom_sheet)!!)
        bottomSheetBehavior.apply {
            isHideable = true
            skipCollapsed = true
        }
    }

    fun show(showListener: (() -> Unit)? = null) {
        if (activity.isFinishing) {
            return
        }
        if (isShowing()) {
            showListener?.invoke()
            return
        }
        dialog.setOnShowListener {
            expandBottomSheet()
            showListener?.invoke()
        }
        dialog.show()
    }

    fun dismiss(dismissListener: (() -> Unit)? = null) {
        if (activity.isFinishing) {
            return
        }
        if (!isShowing()) {
            dismissListener?.invoke()
            dialog.setOnDismissListener(null)
            return
        }
        dialog.setOnDismissListener {
            dismissListener?.invoke()
            dialog.setOnDismissListener(null)
        }
        dialog.dismiss()
    }

    fun isShowing(): Boolean = dialog.isShowing

    fun setTitle(title: String?) {
        contentView.findViewById<TextView>(R.id.tv_title).apply {
            text = title
            visibility = if (title.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    }

    fun setDesc(description: String?) {
        contentView.findViewById<TextView>(R.id.tv_desc).apply {
            text = description
            visibility = if (description.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    }

    private fun setCancelable() {
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun expandBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}
