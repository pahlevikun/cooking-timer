package id.pahlevikun.cookingtimer.di.application

import android.content.Context
import dagger.Module
import dagger.Provides
import id.pahlevikun.cookingtimer.data.local.StorageGateway
import id.pahlevikun.cookingtimer.data.local.StorageGatewayImpl
import id.pahlevikun.cookingtimer.data.local.preference.LanguagePreferencesManager
import id.pahlevikun.cookingtimer.data.local.preference.LanguageServiceManagerImpl
import id.pahlevikun.cookingtimer.data.local.preference.base.PreferenceService
import javax.inject.Singleton

@Module
object StorageModule {

    @Provides
    @Singleton
    fun providePreferenceService(context: Context): PreferenceService {
        return PreferenceService(context)
    }

    @Provides
    @Singleton
    fun provideLanguagePreferenceManager(context: Context): LanguagePreferencesManager {
        return LanguageServiceManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideStorageGateway(
        languagePreferencesManager: LanguagePreferencesManager
    ): StorageGateway {
        return StorageGatewayImpl(languagePreferencesManager)
    }
}