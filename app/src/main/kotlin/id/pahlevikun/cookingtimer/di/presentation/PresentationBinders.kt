package id.pahlevikun.cookingtimer.di.presentation

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.pahlevikun.cookingtimer.di.presentation.viewmodel.ViewModelKey
import id.pahlevikun.cookingtimer.presentation.splash.SplashContract
import id.pahlevikun.cookingtimer.presentation.splash.SplashPresenter
import id.pahlevikun.cookingtimer.presentation.splash.SplashViewModel

@Module
abstract class PresentationBinders {

    @PresentationScope
    @Binds
    abstract fun bindSplashPresenter(presenter: SplashPresenter): SplashContract.Presenter

}