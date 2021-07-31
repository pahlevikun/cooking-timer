package id.pahlevikun.cookingtimer.scheme.appsetting

interface AppSettingProvider {
    val environment: AppEnvironment
    fun getCountrySetting(): LanguageSetting
}