package id.pahlevikun.cookingtimer.common.base.presentation

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import id.pahlevikun.cookingtimer.R
import id.pahlevikun.cookingtimer.common.dialog.LoadingDialog
import id.pahlevikun.cookingtimer.common.helper.LocalizationHelper
import id.pahlevikun.cookingtimer.di.presentation.PresentationComponent
import id.pahlevikun.cookingtimer.di.getPresentationComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    private val component by lazy { getPresentationComponent() }
    private val loadingV2 by lazy { LoadingDialog(this) }

    protected val compositeDisposable = CompositeDisposable()

    @LayoutRes
    abstract fun contentLayoutRes(): Int
    abstract fun injectComponent(component: PresentationComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        injectComponent(component)
        super.onCreate(savedInstanceState)
        setContentView(contentLayoutRes())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalizationHelper.applyLanguageContext(newBase))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    protected fun setToolbar(
        toolbar: Toolbar,
        toolbarTitle: String,
        onBackPress: (() -> Unit)? = null,
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = toolbarTitle
        toolbar.setNavigationOnClickListener {
            if (onBackPress != null) onBackPress.invoke() else onBackPressed()
        }
    }

    protected fun showLoadingModal(
        messageToShow: String,
        title: String = getString(R.string.progress_loading),
    ) {
        loadingV2.apply {
            if (messageToShow.equals(title, ignoreCase = true).not()) {
                setDesc(messageToShow)
            }
            setTitle(title)
        }.show()
    }

    protected fun hideLoadingModal() {
        loadingV2.dismiss()
    }
}