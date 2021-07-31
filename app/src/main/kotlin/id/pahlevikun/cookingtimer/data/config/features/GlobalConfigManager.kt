package id.pahlevikun.cookingtimer.data.config.features

import id.pahlevikun.cookingtimer.data.config.base.ConfigKey
import id.pahlevikun.cookingtimer.data.config.base.ConfigServiceApi

interface GlobalConfigManager {
    fun isEncryptionEnabled(): Boolean
}

class GlobalConfigManagerImpl(private val service: ConfigServiceApi) : GlobalConfigManager {

    override fun isEncryptionEnabled(): Boolean =
        service.getBoolean(ConfigKey.FEATURE_ENCRYPTION_ENABLED)

}