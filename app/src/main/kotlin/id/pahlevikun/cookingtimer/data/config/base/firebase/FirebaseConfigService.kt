package id.pahlevikun.cookingtimer.data.config.base.firebase

import id.pahlevikun.cookingtimer.data.config.base.ConfigServiceApi

class FirebaseConfigService(
    private val firebaseRemoteConfigOverrideValue: FirebaseRemoteConfigOverrideValue,
    private val defaultValueManager: FirebaseRemoteConfigDefaultValue
) : ConfigServiceApi {

    init {
        firebaseRemoteConfigOverrideValue.fetch()
    }

    override fun getString(key: String): String {
        return if (hasConfig(key))
            firebaseRemoteConfigOverrideValue.getString(key)
        else
            defaultValueManager.getString(key)
    }

    override fun getBoolean(key: String): Boolean {
        return if (hasConfig(key))
            firebaseRemoteConfigOverrideValue.getBoolean(key)
        else
            defaultValueManager.getBoolean(key)
    }

    override fun getInt(key: String): Int {
        return if (hasConfig(key))
            firebaseRemoteConfigOverrideValue.getInt(key)
        else
            defaultValueManager.getInt(key)
    }

    override fun getLong(key: String): Long {
        return if (hasConfig(key))
            firebaseRemoteConfigOverrideValue.getLong(key)
        else
            defaultValueManager.getLong(key)
    }

    override fun getDouble(key: String): Double {
        return if (hasConfig(key))
            firebaseRemoteConfigOverrideValue.getDouble(key)
        else
            defaultValueManager.getDouble(key)
    }

    override fun getMapOfValues(key: String): Map<String, Any> {
        return if (hasConfig(key)) {
            FirebaseRemoteConfigHelper.asMapCollection(
                firebaseRemoteConfigOverrideValue.getString(key)
            ) ?: mapOf()
        } else {
            defaultValueManager.getMapOfValues(key)
        }
    }

    private fun hasConfig(key: String): Boolean = firebaseRemoteConfigOverrideValue.hasConfig(key)
}