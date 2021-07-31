package id.pahlevikun.cookingtimer.common.base.presentation

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<V> {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var view: V? = null

    protected fun onAttach(v: V) {
        this.view = v
    }

    protected fun onDetach() {
        this.view = null
        compositeDisposable.clear()
    }

    protected fun view(): V? = view

    protected fun Disposable.disposable() {
        compositeDisposable.add(this)
    }

}