package id.pahlevikun.cookingtimer.common.helper

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import id.pahlevikun.cookingtimer.BuildConfig

object Logger {

    var logEnabled: Boolean = true
    private const val TAG = BuildConfig.APP_NAME

    fun debug(message: String) {
        debug(TAG, message)
    }

    fun debug(tag: String, message: String) {
        if (logEnabled) {
            Log.d(tag, ">> $message")
        }
    }

    fun record(message: String, e: Exception) {
        record(TAG, message, e)
    }

    fun record(tag: String, message: String, e: Exception) {
        if (logEnabled) {
            Log.e(tag, ">> $message", e)
        }
        FirebaseCrashlytics.getInstance().recordException(e)
    }

    fun record(message: String) {
        record(TAG, message)
    }

    fun record(tag: String, message: String) {
        if (logEnabled) {
            Log.d(tag, ">> $message")
        }
        FirebaseCrashlytics.getInstance().log(message)
    }

    fun error(message: String, throwable: Throwable) {
        error(TAG, message, throwable)
    }

    fun error(tag: String, message: String, throwable: Throwable) {
        if (logEnabled) {
            Log.e(tag, ">> $message", throwable)
        }
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

}