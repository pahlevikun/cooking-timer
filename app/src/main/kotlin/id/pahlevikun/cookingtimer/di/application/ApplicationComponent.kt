package id.pahlevikun.cookingtimer.di.application

import dagger.Component
import id.pahlevikun.cookingtimer.LeviApplication
import id.pahlevikun.cookingtimer.data.config.ConfigGateway
import id.pahlevikun.cookingtimer.data.local.StorageGateway
import javax.inject.Singleton

@ApplicationScope
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        StorageModule::class,
        RemoteConfigModule::class
    ]
)
interface ApplicationComponent {

    fun provideStorageGateway(): StorageGateway
    fun provideConfigGateway(): ConfigGateway

    fun inject(application: LeviApplication)
}