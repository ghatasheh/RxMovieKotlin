package reactiveandroid.util

import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

fun Observable<T>.reducePairFirst<T, U>(): Observable<U> where T: Pair<U, *> = reduceWith { it.component1() }

fun Observable<T>.reducePairSecond<T, U>(): Observable<U> where T: Pair<*, U> = reduceWith { it.component2() }

fun Observable<T>.reduceTripleFirst<T, U>(): Observable<U> where T: Triple<U, *, *> = reduceWith { it.component1() }

fun Observable<T>.reduceTripleSecond<T, U>(): Observable<U> where T: Triple<*, U, *> = reduceWith { it.component2() }

fun Observable<T>.reduceTripleThird<T, U>(): Observable<U> where T: Triple<*, *, U> = reduceWith { it.component3() }

fun Observable<T>.reduceQuadFirst<T, U>(): Observable<U> where T: Quad<U, *, *, *> = reduceWith { it.component1() }

fun Observable<T>.reduceQuadSecond<T, U>(): Observable<U> where T: Quad<*, U, *, *> = reduceWith { it.component2() }

fun Observable<T>.reduceQuadThird<T, U>(): Observable<U> where T: Quad<*, *, U, *> = reduceWith { it.component3() }

fun Observable<T>.reduceQuadFourth<T, U>(): Observable<U> where T: Quad<*, *, *, U> = reduceWith { it.component4() }

fun Observable<T>.reduceWith<T, U>(transform: (T) -> U): Observable<U> = compose { source -> source.map(transform) }


