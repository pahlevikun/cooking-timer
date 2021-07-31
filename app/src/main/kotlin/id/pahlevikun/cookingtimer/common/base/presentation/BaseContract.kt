package id.pahlevikun.cookingtimer.common.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface BaseContract {
    interface View {
        fun initViews()
    }

    interface Presenter<V> {
        fun attach(view: V)
        fun detach()
    }

    interface ViewModel<EVENT, ACTION> {
        fun onAction(event: EVENT)

        val state: LiveData<ACTION>
    }
}