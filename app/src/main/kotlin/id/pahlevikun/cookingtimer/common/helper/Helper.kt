package id.pahlevikun.cookingtimer.common.helper

import android.app.Activity
import android.content.*
import android.content.Context.BATTERY_SERVICE
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.provider.MediaStore
import android.telephony.TelephonyManager
import id.pahlevikun.cookingtimer.data.config.ConfigGateway
import id.pahlevikun.cookingtimer.scheme.appsetting.AppEnvironment
import id.pahlevikun.cookingtimer.scheme.appsetting.AppSettingProvider


object Helper {
    const val WIDTH_INDEX = 0
    const val HEIGHT_INDEX = 1

    fun isScreenSizeRetrieved(widthHeight: IntArray): Boolean {
        return widthHeight[WIDTH_INDEX] != 0 && widthHeight[HEIGHT_INDEX] != 0
    }

    fun getRealPathFromURIPath(context: Activity, contentURI: Uri?): String {
        if (contentURI == null) {
            return ""
        }
        val cursor = context.contentResolver.query(
            contentURI,
            null,
            null,
            null,
            null
        )
        return if (cursor == null) {
            contentURI.path.orEmpty()
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx).also { cursor.close() }
        }
    }

    fun copyFileText(context: Context, text: String?) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(android.R.attr.label.toString(), text)
        clipboard.setPrimaryClip(clip)
    }

    fun getBatteryPercentage(context: Context): Int {
        return if (Build.VERSION.SDK_INT >= 21) {
            val bm = context.getSystemService(BATTERY_SERVICE) as BatteryManager
            bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, iFilter)
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val batteryPct = level / scale.toDouble()
            (batteryPct * 100).toInt()
        }
    }

    fun getOperatorName(context: Context): String {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        return manager?.networkOperatorName.orEmpty()
    }

    fun isEncryptionEnabled(context: Context, configGateway: ConfigGateway): Boolean {
        val environment = (context as AppSettingProvider).environment
        return when {
            environment.buildType != AppEnvironment.BuildType.RELEASE -> {
                environment.isDebuggable
            }
            environment.buildType == AppEnvironment.BuildType.RELEASE && environment.isDebuggable -> {
                true
            }
            else -> configGateway.isEncryptionEnabled()
        }
    }

    fun isAtLeastSdkVersion(versionCode: Int): Boolean {
        return Build.VERSION.SDK_INT >= versionCode
    }
}