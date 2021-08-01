package id.pahlevikun.cookingtimer.presentation.splash

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.pahlevikun.cookingtimer.di.presentation.PresentationScope
import id.pahlevikun.cookingtimer.di.presentation.viewmodel.ViewModelKey
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashContent
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashContentImpl
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashNavigation
import id.pahlevikun.cookingtimer.presentation.splash.state.SplashNavigationImpl

@Module
abstract class SplashModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    @PresentationScope
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @PresentationScope
    abstract fun provideSplashNavigation(impl: SplashNavigationImpl): SplashNavigation

    @Binds
    @PresentationScope
    abstract fun provideSplashContent(impl: SplashContentImpl): SplashContent
}