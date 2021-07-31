package id.pahlevikun.cookingtimer.common.extension

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.applyIoSchedulers(): Observable<T> {
    return this
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun Completable.applyIoSchedulers(): Completable {
    return this
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T> Single<T>.applyIoSchedulers(): Single<T> {
    return this
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}