package id.pahlevikun.cookingtimer.data.config

import id.pahlevikun.cookingtimer.data.config.features.AppUpdaterConfigManager
import id.pahlevikun.cookingtimer.data.config.features.GlobalConfigManager

interface ConfigGateway : AppUpdaterConfigManager, GlobalConfigManager

class ConfigManagerImpl(
    private val globalConfigManager: GlobalConfigManager,
    private val appUpdaterConfigManager: AppUpdaterConfigManager
) : ConfigGateway {

    override fun isUpdaterEnabled(): Boolean =
        appUpdaterConfigManager.isUpdaterEnabled()

    override fun getUpdaterOptionalVersion(): String =
        appUpdaterConfigManager.getUpdaterOptionalVersion()

    override fun isUpdaterForceUpdateEnabled(): Boolean =
        appUpdaterConfigManager.isUpdaterForceUpdateEnabled()

    override fun isUpdaterOptionalUpdateEnabled(): Boolean =
        appUpdaterConfigManager.isUpdaterOptionalUpdateEnabled()

    override fun getUpdaterMandatoryVersion(): String =
        appUpdaterConfigManager.getUpdaterMandatoryVersion()

    override fun isUpdaterLockerEnabled(): Boolean =
        appUpdaterConfigManager.isUpdaterLockerEnabled()

    override fun isUpdaterSpecificOsEnabled(): Boolean =
        appUpdaterConfigManager.isUpdaterSpecificOsEnabled()

    override fun getUpdaterSpecificOs(): String =
        appUpdaterConfigManager.getUpdaterSpecificOs()

    override fun isEncryptionEnabled(): Boolean =
        globalConfigManager.isEncryptionEnabled()

    override fun isInAppUpdaterEnabled(): Boolean =
        appUpdaterConfigManager.isInAppUpdaterEnabled()
}