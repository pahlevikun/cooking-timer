package id.pahlevikun.cookingtimer.presentation.splash

import id.pahlevikun.cookingtimer.common.base.presentation.BasePresenter
import id.pahlevikun.cookingtimer.di.presentation.PresentationScope
import javax.inject.Inject

@Deprecated("Unused, delete later, Please use ViewModel")
@PresentationScope
class SplashPresenter @Inject constructor() :
    BasePresenter<SplashContract.View>(),
    SplashContract.Presenter {

    override fun attach(view: SplashContract.View) {
        onAttach(view)
        view()?.initViews()
    }

    override fun detach() {
        onDetach()
    }

}