package id.pahlevikun.cookingtimer.presentation.splash

import id.pahlevikun.cookingtimer.common.base.presentation.BaseViewModel
import id.pahlevikun.cookingtimer.di.presentation.PresentationScope
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashAction
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashEvent
import javax.inject.Inject

@PresentationScope
class SplashViewModel @Inject constructor() : BaseViewModel<SplashEvent, SplashAction>() {

    override fun onAction(event: SplashEvent) {
        when (event) {
            SplashEvent.ViewCreated -> {
                _state.postValue(SplashAction.InitViewComponent)
            }
        }
    }
}