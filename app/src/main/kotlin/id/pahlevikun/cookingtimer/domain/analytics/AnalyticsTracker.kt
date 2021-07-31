package id.pahlevikun.cookingtimer.domain.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import id.pahlevikun.cookingtimer.common.event.GlobalEvent

class AnalyticsTracker(private val firebaseAnalytics: FirebaseAnalytics) {

    fun trackEvent(globalEvent: GlobalEvent) {
        val bundle = Bundle().apply {
            globalEvent.getAttributes().entries.forEach {
                putString(it.key, it.value)
            }
        }
        firebaseAnalytics.logEvent(globalEvent.getName(), bundle)
    }

    fun setUser(userId: String, properties: Map<String, String>) {
        firebaseAnalytics.setUserId(userId)
        properties.entries.forEach {
            firebaseAnalytics.setUserProperty(it.key, it.value)
        }
    }

    fun deleteUser() {
        firebaseAnalytics.resetAnalyticsData()
    }
}