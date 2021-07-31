package id.pahlevikun.cookingtimer.common.dialog

import android.app.Activity
import android.view.View
import android.widget.TextView
import id.pahlevikun.cookingtimer.R
import id.pahlevikun.cookingtimer.common.extension.makeGone
import id.pahlevikun.cookingtimer.common.extension.makeVisible
import id.pahlevikun.cookingtimer.common.view.Button
import id.pahlevikun.cookingtimer.component.clickeffect.setOnSingleClickListener

class GenericDialog(activity: Activity, title: String, isCancelable: Boolean = true) {

    private val contentView =
        activity.layoutInflater.inflate(R.layout.layout_common_dialog_generic, null)
    private val dialog: BaseBottomDialog =
        BaseBottomDialog(activity, title, contentView, isCancelable = isCancelable)

    fun setContent(subTitle: String? = null, content: String) {
        with(contentView) {
            if (subTitle.isNullOrBlank()) {
                findViewById<TextView>(R.id.tv_title).makeGone()
            } else {
                findViewById<TextView>(R.id.tv_title).apply {
                    makeVisible()
                    text = subTitle
                }
            }
            findViewById<TextView>(R.id.tv_title).text = content
        }
    }

    fun setClickListener(onSelected: () -> Unit, onCanceled: (() -> Unit)? = null) {
        contentView.findViewById<Button>(R.id.btn_accept).setOnSingleClickListener {
            onSelected.invoke()
            dialog.dismiss()
        }
        if (onCanceled == null) {
            contentView.findViewById<View>(R.id.divider).makeGone()
            contentView.findViewById<Button>(R.id.btn_reject).makeGone()
        } else {
            contentView.findViewById<Button>(R.id.btn_reject).setOnSingleClickListener {
                dialog.dismiss(onCanceled)
                dialog.userDismissListener = onCanceled
            }
        }
    }

    fun setClickListener(
        positiveTitle: String,
        onSelected: () -> Unit,
        negativeTitle: String? = null,
        onCanceled: (() -> Unit)? = null,
    ) {
        contentView.findViewById<Button>(R.id.btn_accept).text = positiveTitle
        contentView.findViewById<Button>(R.id.btn_accept).setOnSingleClickListener {
            onSelected.invoke()
            dialog.dismiss()
        }
        if (onCanceled == null) {
            contentView.findViewById<View>(R.id.divider).makeGone()
            contentView.findViewById<Button>(R.id.btn_reject).makeGone()
        } else {
            if (negativeTitle.isNullOrBlank()
                    .not()
            ) contentView.findViewById<Button>(R.id.btn_reject).text = negativeTitle
            contentView.findViewById<Button>(R.id.btn_reject).setOnSingleClickListener {
                dialog.dismiss(onCanceled)
                dialog.userDismissListener = onCanceled
            }
        }
    }

    fun show() {
        if (isShow().not()) dialog.show()
    }

    fun isShow() =
        dialog.isShowing()
}