package id.pahlevikun.cookingtimer.presentation.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import id.pahlevikun.cookingtimer.R
import id.pahlevikun.cookingtimer.common.base.presentation.BaseActivity
import id.pahlevikun.cookingtimer.di.presentation.PresentationComponent
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashContent
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashEvent
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashNavigation
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var navigation: SplashNavigation

    @Inject
    internal lateinit var content: SplashContent

    private val viewModel by viewModels<SplashViewModel> { viewModelFactory }

    override fun contentLayoutRes(): Int = R.layout.activity_splash

    override fun injectComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
        viewModel.onAction(SplashEvent.ViewCreated)
    }

    private fun initObserver() {
        navigation.observe(this, viewModel.state)
        content.observe(this, viewModel.state)
    }

}