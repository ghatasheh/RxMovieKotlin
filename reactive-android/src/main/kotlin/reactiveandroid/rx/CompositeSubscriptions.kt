package reactiveandroid.rx

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Kittinun Vantasin on 7/16/15.
 */

fun CompositeSubscription.plusAssign(s: Subscription) = add(s)

