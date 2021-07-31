package id.pahlevikun.cookingtimer.common.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import id.pahlevikun.cookingtimer.R
import id.pahlevikun.cookingtimer.common.extension.makeGone
import id.pahlevikun.cookingtimer.common.extension.makeInvisible
import id.pahlevikun.cookingtimer.common.extension.makeVisible
import id.pahlevikun.cookingtimer.component.clickeffect.setOnSingleClickListener

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.layout_common_view_error, this)
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.DigiErrorView, 0, 0)
        val title = attributeSet.getString(R.styleable.DigiErrorView_digiTitle)
        val description = attributeSet.getString(R.styleable.DigiErrorView_digiDescription)
        val buttonTitle = attributeSet.getString(R.styleable.DigiErrorView_digiAction)
        val image = attributeSet.getResourceId(
            R.styleable.DigiErrorView_digiImageDrawable,
            DEFAULT_IMAGE_VALUE
        )

        try {
            initImage(image)
            initTitle(title)
            initDescription(description)
            initButtonTitle(buttonTitle)
        } finally {
            attributeSet.recycle()
            invalidate()
        }
    }

    private fun initImage(@DrawableRes icon: Int?) {
        if (icon == null || icon == DEFAULT_IMAGE_VALUE) {
            findViewById<ImageView>(R.id.error_image).makeInvisible()
        } else {
            findViewById<ImageView>(R.id.error_image).apply {
                setImageDrawable(AppCompatResources.getDrawable(context, icon))
                makeVisible()
            }
        }
    }

    private fun initTitle(title: String?) {
        if (TextUtils.isEmpty(title)) {
            findViewById<TextView>(R.id.error_title).makeGone()
        } else {
            findViewById<TextView>(R.id.error_title).apply {
                makeVisible()
                text = title
            }
        }
    }

    private fun initDescription(description: String?) {
        if (TextUtils.isEmpty(description)) {
            findViewById<TextView>(R.id.error_description).makeGone()
        } else {
            findViewById<TextView>(R.id.error_description).apply {
                makeVisible()
                text = description
            }
        }
    }

    private fun initButtonTitle(buttonTitle: String?) {
        if (buttonTitle.isNullOrBlank()) {
            findViewById<LinearLayout>(R.id.container_btn_action).makeGone()
        } else {
            findViewById<Button>(R.id.btn_action).text = buttonTitle
            findViewById<LinearLayout>(R.id.container_btn_action).makeVisible()
        }
    }

    fun init(
        @DrawableRes image: Int,
        title: String,
        description: String,
        buttonTitle: String? = null,
        listener: (() -> Unit)? = null,
    ) {
        initImage(image)
        initTitle(title)
        initDescription(description)
        initButtonTitle(buttonTitle)
        setClickListener(listener)
    }

    fun setClickListener(listener: (() -> Unit)?) {
        findViewById<Button>(R.id.btn_action).setOnSingleClickListener { listener?.invoke() }
    }

    companion object {
        private const val DEFAULT_IMAGE_VALUE = -1
    }
}