package id.pahlevikun.cookingtimer.common.helper

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.PowerManager
import java.net.NetworkInterface
import java.util.*

object DeviceUtil {
    private const val PHONE = "Phone"
    private const val TABLET = "Tablet"

    fun getDeviceName(context: Context): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            model.capitalize(LocalizationHelper.getLocale(context))
        } else {
            manufacturer.capitalize(LocalizationHelper.getLocale(context)) + " " + model
        }
    }

    fun getDeviceBrand() = Build.BRAND

    fun getDeviceManufacturer() = Build.MANUFACTURER

    fun getDeviceModel() = Build.MODEL

    fun getDeviceOsVersionName(): String {
        val builder = StringBuilder()
        builder.append("Android ").append(Build.VERSION.RELEASE)

        val fields = Build.VERSION_CODES::class.java.fields
        for (field in fields) {
            val fieldName = field.name
            var fieldValue = -1

            try {
                fieldValue = field.getInt(Any())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" / ").append(fieldName).append(" / ")
                builder.append("Version Code ").append(fieldValue)
            }
        }
        return builder.toString()
    }

    fun getIpAddress(): String {
        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in interfaces) {
                val addressList = Collections.list(networkInterface.inetAddresses)
                for (address in addressList) {
                    if (!address.isLoopbackAddress) {
                        return address.hostAddress
                    }
                }
            }
        } catch (e: Exception) {
            Logger.error("Error getting ip address", e)
        }

        return "NA"
    }

    fun getDeviceType(context: Context): String {
        return if (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE)
            TABLET
        else
            PHONE
    }

    fun getDeviceOs(): String {
        return android.os.Build.VERSION.SDK_INT.toString()
    }

    fun getAppVersionName(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.record("AppVersion package not found", e)
            "NA"
        }

    }

    fun getPackageName(context: Context): String {
        return context.packageName
    }

    fun isLollipopOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    fun isScreenOn(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return if (isLollipopOrAbove()) {
            powerManager.isInteractive
        } else {
            powerManager.isScreenOn
        }
    }

    fun isScreenLocked(context: Context) =
        (context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager).isKeyguardLocked

    fun getUuid(n: Int? = null) =
        UUID.randomUUID().toString().let { if (n != null) it.take(n) else it }

}