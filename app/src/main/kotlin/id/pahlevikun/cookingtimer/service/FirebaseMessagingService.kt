package id.pahlevikun.cookingtimer.service

import android.app.ActivityManager
import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.pahlevikun.cookingtimer.common.helper.Logger
import id.pahlevikun.cookingtimer.di.getDomainComponent

class FirebaseMessagingService : FirebaseMessagingService() {

    private val globalEventHelper by lazy { getDomainComponent().provideGlobalEventHelper() }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Logger.debug("BACKFCM", "From: " + remoteMessage.data.toString())
    }

    private fun isBackgroundRunning(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = am.runningAppProcesses
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == context.packageName) {
                        //If your app is the process in foreground, then it's not in running in background
                        return false
                    }
                }
            }
        }
        return true
    }
}