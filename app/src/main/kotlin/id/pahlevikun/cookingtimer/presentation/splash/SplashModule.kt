package id.pahlevikun.cookingtimer.presentation.splash

import androidx.lifecycle.ViewModel
import com.google.android.datatransport.runtime.dagger.multibindings.IntoMap
import dagger.Binds
import dagger.Module
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
    abstract fun provideSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    abstract fun provideSplashNavigation(impl: SplashNavigationImpl): SplashNavigation

    @Binds
    abstract fun provideSplashContent(impl: SplashContentImpl): SplashContent
}