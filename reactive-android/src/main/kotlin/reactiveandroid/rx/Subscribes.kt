package reactiveandroid.rx

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Kittinun Vantasin on 7/14/15.
 */

public fun <T, U> Observable<T>.liftWith(u: U,
                                  next: U.(T) -> Unit,
                                  error: (U.(Throwable?) -> Unit)? = null,
                                  complete: (U.() -> Unit)? = null): Subscription {

    val subscriptions = CompositeSubscription()
    subscriptions += subscribe({
        u.next(it)
    }, {
        if (error != null) u.error(it)
    }, {
        if (complete != null) u.complete()
        subscriptions.unsubscribe()
    })
    return subscriptions
}
