package com.taskworld.android.domain.network

import fuel.core.Manager
import fuel.core.Request
import fuel.util.build
import rx.Observable
import rx.subjects.ReplaySubject

/**
 * Created by Kittinun Vantasin on 7/8/15.
 */

fun Request.responseAsObservable(): Observable<ByteArray> {
    return Observable.create { subscriber ->

        build(taskRequest) {
            successCallback = { response ->
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(response.data)
                    subscriber.onCompleted()
                }
            }

            failureCallback = { error, response ->
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(error)
                }
            }
        }

        subscriber.onStart()

        submit(taskRequest)
    }
}

fun Request.responseAsStringObservable(): Observable<String> {
    return Observable.create { subscriber ->

        build(taskRequest) {
            successCallback = { response ->

                val data = String(response.data)

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(data)
                    subscriber.onCompleted()
                }
            }

            failureCallback = { error, response ->
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(error)
                }
            }
        }

        subscriber.onStart()

        submit(taskRequest)
    }
}
