package id.pahlevikun.cookingtimer.di.presentation

import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import dagger.Module
import dagger.Provides
import id.pahlevikun.cookingtimer.domain.usecase.GetAppUpdaterConfigUseCase
import id.pahlevikun.cookingtimer.service.AppUpdaterService
import id.pahlevikun.cookingtimer.service.InAppUpdaterService
import id.pahlevikun.cookingtimer.service.InAppUpdaterServiceImpl
import id.pahlevikun.cookingtimer.service.NavigationService

@Module
class ServiceModule {

    @PresentationScope
    @Provides
    fun provideAppUpdaterService(getAppUpdaterConfigUseCase: GetAppUpdaterConfigUseCase) =
        AppUpdaterService(getAppUpdaterConfigUseCase)

    @PresentationScope
    @Provides
    fun providesAppUpdateManager(context: Context) = AppUpdateManagerFactory.create(context)

    @PresentationScope
    @Provides
    fun providedInAppUpdaterService(appUpdateManager: AppUpdateManager): InAppUpdaterService =
        InAppUpdaterServiceImpl(appUpdateManager)

    @PresentationScope
    @Provides
    fun provideNavigationService(): NavigationService = NavigationService()
}