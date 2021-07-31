package id.pahlevikun.cookingtimer.service

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

interface InAppUpdaterService {

    fun initialize(activity: Activity)
    fun startListening()
    fun stopListening()

}

class InAppUpdaterServiceImpl(private val appUpdateManager: AppUpdateManager) :
    InAppUpdaterService {

    private val listener: InstallStateUpdatedListener by lazy {
        InstallStateUpdatedListener { state ->
            when (state.installStatus()) {
                InstallStatus.DOWNLOADED -> {
                }
                InstallStatus.CANCELED -> {
                }
                InstallStatus.DOWNLOADING -> {
                }
                InstallStatus.FAILED -> {
                }
                InstallStatus.INSTALLED -> {
                }
                InstallStatus.INSTALLING -> {
                }
                InstallStatus.PENDING -> {
                }
                InstallStatus.REQUIRES_UI_INTENT -> {
                }
                InstallStatus.UNKNOWN -> {
                }
            }
        }
    }

    override fun initialize(activity: Activity) {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && it.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    it,
                    AppUpdateType.FLEXIBLE,
                    activity,
                    REQUEST_CODE
                )
            }
        }
    }

    override fun startListening() {
        appUpdateManager.registerListener(listener)
    }

    override fun stopListening() {
        appUpdateManager.unregisterListener(listener)
    }

    companion object {
        const val REQUEST_CODE = 311
    }
}