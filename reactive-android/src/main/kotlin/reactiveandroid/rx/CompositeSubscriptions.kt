package reactiveandroid.rx

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Kittinun Vantasin on 7/16/15.
 */

public fun CompositeSubscription.plusAssign(s: Subscription): Unit = add(s)

