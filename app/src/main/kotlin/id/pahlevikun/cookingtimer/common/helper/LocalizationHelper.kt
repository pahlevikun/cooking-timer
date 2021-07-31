package id.pahlevikun.cookingtimer.common.helper

import android.content.Context
import android.os.Build
import androidx.core.os.ConfigurationCompat
import id.pahlevikun.cookingtimer.data.local.preference.LanguagePreferencesManager
import id.pahlevikun.cookingtimer.di.getApplicationComponent
import id.pahlevikun.cookingtimer.scheme.appsetting.LanguageSetting
import id.pahlevikun.cookingtimer.scheme.appsetting.constant.CountryType
import id.pahlevikun.cookingtimer.scheme.appsetting.constant.LanguageType
import java.util.*

object LocalizationHelper {

    private var languageManager: LanguagePreferencesManager? = null

    fun getLocale(context: Context): Locale {
        val languageProvider = getLanguage(context)
        val country = languageProvider.countryCode
        val language = languageProvider.languageCode
        return if (country.isNullOrBlank()) {
            Locale(language)
        } else {
            Locale(language, country)
        }
    }

    fun applyLanguageContext(context: Context): Context {
        try {
            val locale = getLocale(context)
            val configuration = context.resources.configuration
            val displayMetrics = context.resources.displayMetrics

            Locale.setDefault(locale)

            return if (Helper.isAtLeastSdkVersion(Build.VERSION_CODES.JELLY_BEAN_MR1)) {
                configuration.setLocale(locale)
                context.createConfigurationContext(configuration)
            } else {
                configuration.locale = locale
                context.resources.updateConfiguration(configuration, displayMetrics)
                context
            }
        } catch (exception: Exception) {
            return context
        }
    }

    fun getLanguage(context: Context): LanguageSetting {
        if (languageManager == null) languageManager = context
            .getApplicationComponent()
            .provideStorageGateway()
        return languageManager?.getLanguage() ?: getSystemLanguage(context)
    }

    private fun getSystemLanguage(context: Context): LanguageSetting {
        val locale = getSystemLocale(context)
        return if (locale.country.equals(CountryType.INDONESIA, ignoreCase = true)) {
            if (locale.language.equals(Locale(LanguageType.ENGLISH))) {
                LanguageSetting.Country.Indonesia.english()
            } else {
                LanguageSetting.Country.Indonesia.bahasa()
            }
        } else {
            LanguageSetting(
                countryName = locale.displayCountry,
                languageCode = LanguageType.ENGLISH
            )
        }
    }

    private fun getSystemLocale(context: Context): Locale {
        return try {
            ConfigurationCompat.getLocales(context.resources.configuration).get(0)
        } catch (e: Exception) {
            getLocale(context)
        }
    }

}