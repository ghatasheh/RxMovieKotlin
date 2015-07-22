package reactiveandroid.rx

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

public interface Component1<out T> {
    fun component1(): T
}

public interface Component2<out T> {
    fun component2(): T
}

public interface Component3<out T> {
    fun component3(): T
}

public interface Component4<out T> {
    fun component4(): T
}

public fun <T : Component1<A>, A> Observable<T>.tupleFirst(): Observable<A> = map { it.component1() }
public fun <T : Component2<B>, B> Observable<T>.tupleSecond(): Observable<B> = map { it.component2() }
public fun <T : Component3<C>, C> Observable<T>.tupleThird(): Observable<C> = map { it.component3() }
public fun <T : Component4<D>, D> Observable<T>.tupleFourth(): Observable<D> = map { it.component4() }

public fun <T : Tuple2<A, B>, A, B, R> Observable<T>.reduce(f: (A, B) -> R) : Observable<R> =
        map { f(it.component1(), it.component2()) }

public fun <T : Tuple3<A, B, C>, A, B, C, R> Observable<T>.reduce(f: (A, B, C) -> R) : Observable<R> =
        map { f(it.component1(), it.component2(), it.component3()) }

public fun <T : Tuple4<A, B, C, D>, A, B, C, D, R> Observable<T>.reduce(f: (A, B, C, D) -> R) : Observable<R> =
        map { f(it.component1(), it.component2(), it.component3(), it.component4()) }

public fun <T, U> Observable<T>.liftWith(u: U,
                                  next: U.(T) -> Unit,
                                  error: (U.(Throwable?) -> Unit)? = null,
                                  complete: (U.() -> Unit)? = null): Subscription {

    val subscriptions = CompositeSubscription()
    subscriptions += subscribe({
        u.next(it)
    }, {
        if (error != null) u?.error(it)
        subscriptions.unsubscribe()
    }, {
        if (complete != null) u.complete()
        subscriptions.unsubscribe()
    })
    return subscriptions
}

public fun <T : Tuple2<A, B>, A, B> Observable<T>.subscribe(next: (A, B) -> Unit): Subscription {
    return subscribe(object : Subscriber<T>() {

        override fun onNext(t: T) {
            val (t1, t2) = t
            next(t1, t2)
        }

        override fun onError(e: Throwable?) {
        }

        override fun onCompleted() {
        }

    })
}

public fun <T : Tuple2<A, B>, A, B> Observable<T>.subscribe(next: (A, B) -> Unit,
                                            error: ((e: Throwable?) -> Unit)? = null,
                                            complete: (() -> Unit)? = null): Subscription {
    return subscribe(object : Subscriber<T>() {

        override fun onNext(t: T) {
            val (t1, t2) = t
            next(t1, t2)
        }

        override fun onError(e: Throwable?) {
            error?.invoke(e)
        }

        override fun onCompleted() {
            complete?.invoke()
        }

    })
}

public fun <T : Tuple3<A, B, C>, A, B, C> Observable<T>.subscribe(next: (A, B, C) -> Unit): Subscription {
    return subscribe(object : Subscriber<T>() {

        override fun onNext(t: T) {
            val (t1, t2, t3) = t
            next(t1, t2, t3)
        }

        override fun onError(e: Throwable?) {
        }

        override fun onCompleted() {
        }

    })
}

public fun <T : Tuple3<A, B, C>, A, B, C> Observable<T>.subscribe(next: (A, B, C) -> Unit,
                                                                  error: ((e: Throwable?) -> Unit)? = null,
                                                                  complete: (() -> Unit)? = null): Subscription {
    return subscribe(object : Subscriber<T>() {

        override fun onNext(t: T) {
            val (t1, t2, t3) = t
            next(t1, t2, t3)
        }

        override fun onError(e: Throwable?) {
            error?.invoke(e)
        }

        override fun onCompleted() {
            complete?.invoke()
        }

    })
}

public fun <T : Tuple4<A, B, C, D>, A, B, C, D> Observable<T>.subscribe(next: (A, B, C, D) -> Unit): Subscription {
    return subscribe(object : Subscriber<T>() {

        override fun onNext(t: T) {
            val (t1, t2, t3, t4) = t
            next(t1, t2, t3, t4)
        }

        override fun onError(e: Throwable?) {
        }

        override fun onCompleted() {
        }

    })
}

public fun <T : Tuple4<A, B, C, D>, A, B, C, D> Observable<T>.subscribe(next: (A, B, C, D) -> Unit,
                                                                        error: ((e: Throwable?) -> Unit)? = null,
                                                                        complete: (() -> Unit)? = null): Subscription {
    return subscribe(object : Subscriber<T>() {

        override fun onNext(t: T) {
            val (t1, t2, t3, t4) = t
            next(t1, t2, t3, t4)
        }

        override fun onError(e: Throwable?) {
            error?.invoke(e)
        }

        override fun onCompleted() {
            complete?.invoke()
        }

    })
}


