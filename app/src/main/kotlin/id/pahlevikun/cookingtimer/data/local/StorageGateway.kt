package id.pahlevikun.cookingtimer.data.local

import id.pahlevikun.cookingtimer.data.local.preference.LanguagePreferencesManager
import id.pahlevikun.cookingtimer.scheme.appsetting.LanguageSetting

interface StorageGateway : LanguagePreferencesManager

class StorageGatewayImpl(
    private val languagePreferencesManager: LanguagePreferencesManager
) : StorageGateway {

    override fun saveLanguage(data: LanguageSetting) =
        languagePreferencesManager.saveLanguage(data)

    override fun getLanguage(): LanguageSetting =
        languagePreferencesManager.getLanguage()
}