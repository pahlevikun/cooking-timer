package id.pahlevikun.cookingtimer.service

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

class NavigationService {

    fun navigateToHomeScreen(activity: Activity?) {
        activity?.run { startActivity(Intent(activity, Activity::class.java)) }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun goToOverlaySettings(activity: Activity?) {
        activity?.run {
            startActivityForResult(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ), REQUEST_PERMISSION_LOGIN
            )
        }
    }

    companion object {
        const val REQUEST_PERMISSION_LOGIN = 1
    }
}