package id.pahlevikun.cookingtimer.data.config.base.firebase

import android.os.Handler
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import id.pahlevikun.cookingtimer.scheme.appsetting.AppSettingProvider

interface FirebaseRemoteConfigOverrideValue {

    fun fetch()

    fun getString(key: String): String

    fun getBoolean(key: String): Boolean

    fun getInt(key: String): Int

    fun getLong(key: String): Long

    fun getDouble(key: String): Double

    fun hasConfig(key: String): Boolean
}

class FirebaseRemoteConfigOverrideValueImpl(
    private val appSettingProvider: AppSettingProvider,
    private val defaultConfig: HashMap<String, Any>
) : FirebaseRemoteConfigOverrideValue {

    private val isDebug by lazy { appSettingProvider.environment.isDebuggable }

    override fun fetch() {
        Handler().post {
            val remoteConfig = Firebase.remoteConfig.apply {
                val configSettings = remoteConfigSettings {
                    minimumFetchIntervalInSeconds = if (isDebug) 0L else DEFAULT_CACHE_REMOTE_CONFIG
                }
                setConfigSettingsAsync(configSettings)
                setDefaultsAsync(defaultConfig)
            }
            val cacheExpiration = if (isDebug) 0L else DEFAULT_CACHE_REMOTE_CONFIG
            remoteConfig.fetch(cacheExpiration).addOnCompleteListener { task ->
                if (task.isSuccessful) remoteConfig.activate()
            }
        }
    }

    override fun getString(key: String): String {
        return Firebase.remoteConfig.getValue(key).asString()
    }

    override fun getBoolean(key: String): Boolean {
        return Firebase.remoteConfig.getValue(key).asBoolean()
    }

    override fun getInt(key: String): Int {
        return Firebase.remoteConfig.getValue(key).asLong().toInt()
    }

    override fun getLong(key: String): Long {
        return Firebase.remoteConfig.getValue(key).asLong()
    }

    override fun getDouble(key: String): Double {
        return Firebase.remoteConfig.getValue(key).asDouble()
    }

    override fun hasConfig(key: String): Boolean =
        Firebase.remoteConfig.getValue(key).source != 0

    private companion object {
        const val DEFAULT_CACHE_REMOTE_CONFIG = 3600L
    }

}