package id.pahlevikun.cookingtimer.common.base.presentation

interface BaseContract {
    interface View {
        fun initViews()
    }

    interface Presenter<V> {
        fun attach(view: V)
        fun detach()
    }

    interface ViewModel<EVENT> {
        fun onAction(event: EVENT)
    }
}