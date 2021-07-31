package id.pahlevikun.cookingtimer.common.base.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


abstract class BaseViewModel<EVENT, ACTION> :
    BaseContract.ViewModel<EVENT, ACTION>,
    ViewModel() {

    override val state: LiveData<ACTION> = _state

    @Suppress("PropertyName")
    protected val _state: MutableLiveData<ACTION>
        get() = MutableLiveData<ACTION>()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun Disposable.disposable() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}