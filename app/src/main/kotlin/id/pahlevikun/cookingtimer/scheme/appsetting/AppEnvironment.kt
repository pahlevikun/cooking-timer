package id.pahlevikun.cookingtimer.scheme.appsetting

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppEnvironment(
    val appName: String,
    val appVersion: String,
    val applicationId: String,
    val appVersionCode: Int,
    val isDebuggable: Boolean,
    val buildType: BuildType,
    val languageSetting: LanguageSetting
) : Parcelable {

    enum class BuildType {
        DEBUG, TRIAL, RELEASE;

        companion object {
            fun parse(type: String): BuildType {
                return try {
                    java.lang.Enum.valueOf(BuildType::class.java, type)
                } catch (e: Exception) {
                    DEBUG
                }
            }
        }
    }
}
