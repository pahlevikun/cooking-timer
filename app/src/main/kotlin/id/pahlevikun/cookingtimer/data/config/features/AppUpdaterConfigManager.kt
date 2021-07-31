package id.pahlevikun.cookingtimer.data.config.features

import id.pahlevikun.cookingtimer.data.config.base.ConfigKey
import id.pahlevikun.cookingtimer.data.config.base.ConfigServiceApi

interface AppUpdaterConfigManager {
    fun isUpdaterEnabled(): Boolean
    fun isUpdaterLockerEnabled(): Boolean
    fun getUpdaterOptionalVersion(): String
    fun isUpdaterForceUpdateEnabled(): Boolean
    fun isUpdaterOptionalUpdateEnabled(): Boolean
    fun isUpdaterSpecificOsEnabled(): Boolean
    fun getUpdaterSpecificOs(): String
    fun getUpdaterMandatoryVersion(): String
    fun isInAppUpdaterEnabled(): Boolean
}

class AppUpdaterConfigManagerImpl(private val service: ConfigServiceApi) : AppUpdaterConfigManager {

    override fun isUpdaterEnabled(): Boolean =
        service.getBoolean(ConfigKey.FEATURE_UPDATER_ENABLED)

    override fun getUpdaterOptionalVersion(): String =
        service.getString(ConfigKey.FEATURE_UPDATER_OPTIONAL_MIN_VERSION)

    override fun isUpdaterForceUpdateEnabled(): Boolean =
        service.getBoolean(ConfigKey.FEATURE_UPDATER_FORCE_UPDATE_ENABLED)

    override fun isUpdaterOptionalUpdateEnabled(): Boolean =
        service.getBoolean(ConfigKey.FEATURE_UPDATER_OPTIONAL_UPDATE_ENABLED)

    override fun getUpdaterMandatoryVersion(): String =
        service.getString(ConfigKey.FEATURE_UPDATER_MANDATORY_MIN_VERSION)

    override fun isUpdaterLockerEnabled(): Boolean =
        service.getBoolean(ConfigKey.FEATURE_UPDATER_LOCKER_ENABLED)

    override fun isUpdaterSpecificOsEnabled(): Boolean =
        service.getBoolean(ConfigKey.FEATURE_UPDATER_SPECIFIC_OS_ENABLED)

    override fun getUpdaterSpecificOs(): String =
        service.getString(ConfigKey.FEATURE_UPDATER_SPECIFIC_OS_VERSION)

    override fun isInAppUpdaterEnabled(): Boolean =
        service.getBoolean(ConfigKey.FEATURE_UPDATER_IN_APP_ENABLED)
}