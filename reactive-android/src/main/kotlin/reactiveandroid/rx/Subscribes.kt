package reactiveandroid.rx

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Kittinun Vantasin on 7/14/15.
 */

fun <T, U> Observable<T>.liftWith(u: U, next: U.(T) -> Unit, error: (U.(Throwable?) -> Unit)? = null): Subscription {
    val subscriptions = CompositeSubscription()
    subscriptions += subscribe(object : Subscriber<T>() {

        override fun onError(e: Throwable?) {
            if (error != null) u.error(e)
        }

        override fun onCompleted() {
            subscriptions.unsubscribe()
        }

        override fun onNext(t: T) {
            u.next(t)
        }
    })
    return subscriptions
}
