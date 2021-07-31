package id.pahlevikun.cookingtimer.common.dialog

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBindings
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.pahlevikun.cookingtimer.R

class BaseBottomDialog(
    private val context: Context,
    private val title: String? = null,
    contentView: View,
    isCancelable: Boolean = false
) {

    var userDismissListener: (() -> Unit)? = null
        set(value) {
            field = value
            dialog.setOnDismissListener {
                userDismissListener?.invoke()
                dialog.setOnDismissListener(null)
            }
        }

    private val dialog: BottomSheetDialog = BottomSheetDialog(context)
    private val bottomSheetBehavior: BottomSheetBehavior<View>

    init {
        val dialogView = LayoutInflater
            .from(context)
            .inflate(
                R.layout.layout_base_dialog_bottom,
                dialog.findViewById(android.R.id.content),
                false
            ).apply {
                findViewById<ViewGroup>(R.id.content_container).addView(contentView)
                setTitle(this, title)
                setCancelable(this, isCancelable)
                setDismissButtonClickListener(this)
            }
        dialog.apply {
            setContentView(dialogView)
            setOnCancelListener { }
        }
        bottomSheetBehavior =
            BottomSheetBehavior.from(dialog.findViewById(R.id.design_bottom_sheet)!!)
        bottomSheetBehavior.apply {
            isHideable = true
            skipCollapsed = true
        }
    }

    fun show(showListener: (() -> Unit)? = null) {
        if ((context is Activity) && context.isFinishing) {
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
        if ((context is Activity) && context.isFinishing) {
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

    private fun setTitle(dialogView: View, title: String?) {
        dialogView.findViewById<TextView>(R.id.tv_dialog_title).apply {
            text = title
            visibility = if (title.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    }

    private fun setDismissButtonClickListener(dialogView: View) {
        dialogView.findViewById<ImageView>(R.id.iv_cancel).setOnClickListener {
            dismiss(userDismissListener)
        }
    }

    private fun setCancelable(dialogView: View, cancelable: Boolean) {
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(cancelable)
        dialogView.findViewById<ImageView>(R.id.iv_cancel).visibility = if (cancelable) View.VISIBLE else View.GONE
    }

    private fun expandBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}
