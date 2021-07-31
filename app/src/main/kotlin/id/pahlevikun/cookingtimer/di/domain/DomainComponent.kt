package id.pahlevikun.cookingtimer.di.domain

import dagger.Component
import id.pahlevikun.cookingtimer.common.event.GlobalEventHelper
import id.pahlevikun.cookingtimer.di.application.ApplicationComponent

@DomainScope
@Component(
    modules = [
        AnalyticsModule::class,
        UseCaseBinders::class
    ],
    dependencies = [ApplicationComponent::class]
)
interface DomainComponent {

    @Component.Builder
    interface Builder {

        fun component(component: ApplicationComponent): Builder

        fun build(): DomainComponent
    }

    fun provideGlobalEventHelper(): GlobalEventHelper
}