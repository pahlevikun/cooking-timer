package id.pahlevikun.cookingtimer.common

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import id.pahlevikun.cookingtimer.common.helper.Logger
import id.pahlevikun.jotter.Jotter

object JotterListener : Jotter.Listener {
    override fun onReceiveActivityEvent(
        activity: Activity,
        event: String,
        bundle: Bundle?
    ) {
        Logger.record("LifeCycleCallback", "ACTIVITY: $activity >>> $event")
    }

    override fun onReceiveFragmentEvent(
        fragment: Fragment,
        context: Context?,
        event: String,
        bundle: Bundle?
    ) {
        Logger.record("LifeCycleCallback", "FRAGMENT: $fragment >>> $event")
    }
}