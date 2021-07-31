package id.pahlevikun.cookingtimer.data.local.preference

import android.content.Context
import id.pahlevikun.cookingtimer.BuildConfig
import id.pahlevikun.cookingtimer.data.local.preference.base.BasePreferenceService
import id.pahlevikun.cookingtimer.scheme.appsetting.LanguageSetting
import id.pahlevikun.cookingtimer.scheme.appsetting.constant.LanguageType

interface LanguagePreferencesManager {
    fun saveLanguage(data: LanguageSetting)
    fun getLanguage(): LanguageSetting
}

class LanguageServiceManagerImpl(context: Context) :
    BasePreferenceService(context, PREF_CONSENT),
    LanguagePreferencesManager {

    override fun saveLanguage(data: LanguageSetting) {
        putObject(KEY_LANGUAGE_DATA, data)
    }

    override fun getLanguage(): LanguageSetting {
        return try {
            val defaultLanguage = if (getString(KEY_LANGUAGE_DATA, DEFAULT_LANGUAGE_DATA)
                    .contains(LanguageType.ENGLISH)
            ) {
                LanguageSetting.Country.Indonesia.english()
            } else {
                LanguageSetting.Country.Indonesia.bahasa()
            }
            getObject(KEY_LANGUAGE_DATA, defaultLanguage, LanguageSetting::class.java)
        } catch (exception: Exception) {
            LanguageSetting.Country.Indonesia.bahasa()
        }
    }

    companion object {
        private const val PREF_CONSENT = "LanguagePreference"

        private const val KEY_LANGUAGE_DATA =
            "${BuildConfig.APPLICATION_ID}.local.preferences.language"

        private const val DEFAULT_LANGUAGE_DATA = "id"
    }
}