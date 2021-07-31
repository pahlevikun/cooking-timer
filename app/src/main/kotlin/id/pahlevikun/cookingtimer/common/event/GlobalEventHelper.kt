package id.pahlevikun.cookingtimer.common.event

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject

class GlobalEventHelper(type: String = TYPE_DEFAULT) {

    private val event = when (type) {
        TYPE_REPLAY -> ReplaySubject.create<GlobalEvent>().toSerialized()
        else -> PublishSubject.create<GlobalEvent>().toSerialized()
    }

    fun sendEvent(o: GlobalEvent) {
        event.onNext(o)
    }

    fun getObservable(): Observable<GlobalEvent> {
        return event
    }

    companion object {
        const val TYPE_DEFAULT = "default"
        const val TYPE_REPLAY = "replay"

        private var instance: GlobalEventHelper? = null
        private var nonDefaultInstance: GlobalEventHelper? = null

        val default: GlobalEventHelper
            get() {
                if (instance == null) {
                    instance = GlobalEventHelper()
                }
                return instance!!
            }

        val replay: GlobalEventHelper
            get() {
                if (nonDefaultInstance == null) {
                    nonDefaultInstance = GlobalEventHelper(TYPE_REPLAY)
                }
                return nonDefaultInstance!!
            }
    }
}