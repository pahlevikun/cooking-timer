package id.pahlevikun.cookingtimer.presentation.splash.state

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import id.pahlevikun.cookingtimer.di.presentation.PresentationScope
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashAction.ForceUpdate
import id.pahlevikun.cookingtimer.service.NavigationService
import javax.inject.Inject

interface SplashNavigation {
    fun observe(
        activity: AppCompatActivity,
        action: LiveData<SplashAction>,
    )
}

@PresentationScope
class SplashNavigationImpl @Inject constructor(
    private val navigator: NavigationService,
) : SplashNavigation {

    override fun observe(
        activity: AppCompatActivity,
        action: LiveData<SplashAction>,
    ) {
        action.observe(activity, Observer {
            when (it) {
                ForceUpdate -> {
                }
                SplashAction.GoToHomeScreen -> {
                }
            }
        })
    }
}