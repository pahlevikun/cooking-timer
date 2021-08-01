package id.pahlevikun.cookingtimer.di.presentation

import dagger.Component
import id.pahlevikun.cookingtimer.common.event.GlobalEventHelper
import id.pahlevikun.cookingtimer.di.domain.DomainComponent
import id.pahlevikun.cookingtimer.di.presentation.viewmodel.ViewModelModule
import id.pahlevikun.cookingtimer.presentation.splash.SplashActivity
import id.pahlevikun.cookingtimer.presentation.splash.SplashModule

@PresentationScope
@Component(
    modules = [ServiceModule::class, ViewModelModule::class, SplashModule::class],
    dependencies = [DomainComponent::class]
)
interface PresentationComponent {

    @Component.Builder
    interface Builder {

        fun component(component: DomainComponent): Builder

        fun build(): PresentationComponent
    }

    fun provideGlobalEventHelper(): GlobalEventHelper
    fun inject(activity: SplashActivity)
}