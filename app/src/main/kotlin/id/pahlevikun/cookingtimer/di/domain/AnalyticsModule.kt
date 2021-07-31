package id.pahlevikun.cookingtimer.di.domain

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import id.pahlevikun.cookingtimer.common.event.GlobalEventHelper
import id.pahlevikun.cookingtimer.domain.analytics.AnalyticsTracker

@Module
class AnalyticsModule {

    @DomainScope
    @Provides
    fun provideAnalyticsTracker(firebaseAnalytics: FirebaseAnalytics) =
        AnalyticsTracker(firebaseAnalytics)

    @DomainScope
    @Provides
    fun provideFirebaseAnalytics(context: Context) =
        FirebaseAnalytics.getInstance(context)

    @DomainScope
    @Provides
    fun provideEventHelper() = GlobalEventHelper.default
}