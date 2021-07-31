package id.pahlevikun.cookingtimer.data.config.base.firebase

import id.pahlevikun.cookingtimer.data.config.base.ConfigKey
import id.pahlevikun.cookingtimer.data.config.base.ConfigServiceApi

interface FirebaseRemoteConfigDefaultValue : ConfigServiceApi {
    fun getDefaultValues(): HashMap<String, Any>
}

object FirebaseRemoteConfigDefaultValueImpl : FirebaseRemoteConfigDefaultValue {

    private val DATA = hashMapOf<String, Any>(
        ConfigKey.FEATURE_UPDATER_ENABLED to false,
        ConfigKey.FEATURE_UPDATER_FORCE_UPDATE_ENABLED to false,
        ConfigKey.FEATURE_UPDATER_MANDATORY_MIN_VERSION to "1.0.0",
        ConfigKey.FEATURE_UPDATER_OPTIONAL_MIN_VERSION to "1.0.0",
        ConfigKey.FEATURE_UPDATER_OPTIONAL_UPDATE_ENABLED to false,
        ConfigKey.FEATURE_UPDATER_LOCKER_ENABLED to false,
        ConfigKey.FEATURE_UPDATER_SPECIFIC_OS_ENABLED to false,
        ConfigKey.FEATURE_UPDATER_SPECIFIC_OS_VERSION to "5.0.0",
        ConfigKey.FEATURE_UPDATER_IN_APP_ENABLED to false
    )

    override fun getDefaultValues(): HashMap<String, Any> {
        return DATA
    }

    override fun getString(key: String): String {
        return getDefaultValues()[key]?.toString().orEmpty()
    }

    override fun getBoolean(key: String): Boolean {
        return FirebaseRemoteConfigHelper.asBoolean(
            getDefaultValues()[key]?.toString().orEmpty()
        )
    }

    override fun getInt(key: String): Int {
        return getDefaultValues()[key]?.toString()?.toIntOrNull() ?: 0
    }

    override fun getLong(key: String): Long {
        return getDefaultValues()[key]?.toString()?.toLongOrNull() ?: 0L
    }

    override fun getDouble(key: String): Double {
        return getDefaultValues()[key]?.toString()?.toDoubleOrNull() ?: 0.0
    }

    override fun getMapOfValues(key: String): HashMap<String, Any> {
        return FirebaseRemoteConfigHelper.asMapCollection(
            getDefaultValues()[key]?.toString().orEmpty()
        ) ?: hashMapOf()
    }
}