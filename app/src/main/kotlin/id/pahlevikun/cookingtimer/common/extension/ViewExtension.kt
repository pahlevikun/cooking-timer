package id.pahlevikun.cookingtimer.common.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.File


fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun ImageView.rotateWithAnimation(value: Float) {
    this.animate().rotation(value).setDuration(300).start()
}

fun View.makeEnable() {
    this.isEnabled = true
}

fun View.makeDisable() {
    this.isEnabled = false
}

fun EditText.clearText() {
    this.text?.clear()
}

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun View.setOnSingleClickListener(
    debounceTime: Long = 400L,
    action: ((view: View) -> Unit)? = null,
) {
    setOnClickListener(object : View.OnClickListener {
        private var allowClick: Boolean = true

        override fun onClick(v: View) {
            if (allowClick && action != null) {
                action.invoke(v)
                Handler().postDelayed({ allowClick = true }, debounceTime)
            }
        }
    })
}

fun View.motionClick(): Boolean {
    val downTime = SystemClock.uptimeMillis()
    val eventTime = SystemClock.uptimeMillis() + 100
    val x = 0.0f
    val y = 0.0f

    val metaState = 0
    val motionEvent = MotionEvent.obtain(
        downTime,
        eventTime,
        MotionEvent.ACTION_UP,
        x,
        y,
        metaState
    )
    performClick()
    return dispatchTouchEvent(motionEvent)
}

fun ImageView.loadImageFromUri(
    context: Context,
    uri: String?,
    placeHolder: Int? = null,
    errorPlaceHolder: Int? = null,
    onLoaded: ((isSuccessFul: Boolean) -> Unit)? = null,
) {
    if (!uri.isNullOrBlank()) {
        Glide
            .with(context)
            .asBitmap()
            .load(uri)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .apply {
                if (placeHolder != null) placeholder(
                    AppCompatResources.getDrawable(
                        context,
                        placeHolder
                    )
                )
                if (errorPlaceHolder != null) error(
                    AppCompatResources.getDrawable(
                        context,
                        errorPlaceHolder
                    )
                )
            }
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    onLoaded?.invoke(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    onLoaded?.invoke(true)
                    return false
                }
            })
            .into(this)
    } else {
        onLoaded?.invoke(false)
    }
}

fun ImageView.loadImageFromFile(
    file: File,
    isRoundedCorner: Boolean = false,
    placeHolder: Int? = null,
    errorPlaceHolder: Int? = null,
    onLoaded: ((isSuccessFul: Boolean) -> Unit)? = null,
) {
    Glide
        .with(context)
        .setDefaultRequestOptions(RequestOptions().apply {
            if (placeHolder != null) placeholder(placeHolder)
            if (errorPlaceHolder != null) error(errorPlaceHolder)
            if (isRoundedCorner) transform(RoundedCorners(8))
            centerCrop()
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        })
        .load(file)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean,
            ): Boolean {
                onLoaded?.invoke(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean,
            ): Boolean {
                onLoaded?.invoke(true)
                return false
            }
        })
        .into(this)
}