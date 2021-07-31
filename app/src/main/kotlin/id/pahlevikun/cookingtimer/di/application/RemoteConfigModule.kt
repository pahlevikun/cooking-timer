package id.pahlevikun.cookingtimer.di.application

import android.content.Context
import dagger.Module
import dagger.Provides
import id.pahlevikun.cookingtimer.data.config.ConfigGateway
import id.pahlevikun.cookingtimer.data.config.ConfigManagerImpl
import id.pahlevikun.cookingtimer.data.config.base.firebase.*
import id.pahlevikun.cookingtimer.data.config.features.AppUpdaterConfigManager
import id.pahlevikun.cookingtimer.data.config.features.AppUpdaterConfigManagerImpl
import id.pahlevikun.cookingtimer.data.config.features.GlobalConfigManager
import id.pahlevikun.cookingtimer.data.config.features.GlobalConfigManagerImpl
import id.pahlevikun.cookingtimer.scheme.appsetting.AppSettingProvider
import javax.inject.Singleton

@Module
object RemoteConfigModule {

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfigDefaultValue(): FirebaseRemoteConfigDefaultValue {
        return FirebaseRemoteConfigDefaultValueImpl
    }

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfigOverrideValue(
        context: Context,
        firebaseRemoteConfigDefaultValue: FirebaseRemoteConfigDefaultValue
    ): FirebaseRemoteConfigOverrideValue {
        return FirebaseRemoteConfigOverrideValueImpl(
            appSettingProvider = (context as AppSettingProvider),
            defaultConfig = firebaseRemoteConfigDefaultValue.getDefaultValues()
        )
    }

    @Provides
    @Singleton
    fun providesFirebaseRemoteConfigService(
        firebaseRemoteConfigOverrideValue: FirebaseRemoteConfigOverrideValue,
        firebaseRemoteConfigDefaultValue: FirebaseRemoteConfigDefaultValue
    ): FirebaseConfigService {
        return FirebaseConfigService(
            firebaseRemoteConfigOverrideValue = firebaseRemoteConfigOverrideValue,
            defaultValueManager = firebaseRemoteConfigDefaultValue
        )
    }

    @Provides
    @Singleton
    fun providesAppUpdaterConfig(firebaseRemoteConfigService: FirebaseConfigService): AppUpdaterConfigManager {
        return AppUpdaterConfigManagerImpl(service = firebaseRemoteConfigService)
    }

    @Provides
    @Singleton
    fun providesGlobalConfig(firebaseRemoteConfigService: FirebaseConfigService): GlobalConfigManager {
        return GlobalConfigManagerImpl(service = firebaseRemoteConfigService)
    }

    @Provides
    @Singleton
    fun providesConfigService(
        appUpdaterConfigManager: AppUpdaterConfigManager,
        globalConfigManager: GlobalConfigManager
    ): ConfigGateway {
        return ConfigManagerImpl(
            appUpdaterConfigManager = appUpdaterConfigManager,
            globalConfigManager = globalConfigManager
        )
    }
}