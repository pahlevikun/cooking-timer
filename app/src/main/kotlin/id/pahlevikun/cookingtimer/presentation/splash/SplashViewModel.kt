package id.pahlevikun.cookingtimer.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.pahlevikun.cookingtimer.common.base.presentation.BaseContract
import id.pahlevikun.cookingtimer.common.base.presentation.BaseViewModel
import id.pahlevikun.cookingtimer.di.presentation.PresentationScope
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashAction
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashEvent
import javax.inject.Inject

@PresentationScope
class SplashViewModel @Inject constructor() : BaseViewModel(), BaseContract.ViewModel<SplashEvent> {

    val state: LiveData<SplashAction>
        get() = _state

    private val _state = MutableLiveData<SplashAction>()

    override fun onAction(event: SplashEvent) {
        when (event) {
            SplashEvent.ViewCreated -> {
            }
        }
    }
}