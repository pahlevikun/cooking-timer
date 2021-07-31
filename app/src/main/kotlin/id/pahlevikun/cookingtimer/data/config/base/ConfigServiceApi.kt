package id.pahlevikun.cookingtimer.data.config.base

interface ConfigServiceApi {
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
    fun getInt(key: String): Int
    fun getLong(key: String): Long
    fun getDouble(key: String): Double
    fun getMapOfValues(key: String): Map<String, Any>
}