package util

import rx.Observable
import rx.Subscriber
import rx.Subscription

/**
 * Created by Kittinun Vantasin on 7/14/15.
 */

fun <T, U> U.liftObservable(o: Observable<T>, onNext: U.(T) -> Unit): Subscription {
    return o.subscribe(object : Subscriber<T>(){

        override fun onError(e: Throwable?) {
        }

        override fun onCompleted() {
        }

        override fun onNext(t: T) {
            onNext(t)
        }
    })
}
