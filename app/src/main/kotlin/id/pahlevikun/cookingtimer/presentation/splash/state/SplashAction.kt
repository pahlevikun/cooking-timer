package id.pahlevikun.cookingtimer.presentation.splash.state

sealed class SplashAction {
    object ForceUpdate : SplashAction()
    object GoToHomeScreen : SplashAction()
}