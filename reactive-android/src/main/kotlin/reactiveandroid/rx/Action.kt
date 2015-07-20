package reactiveandroid.rx

import org.jetbrains.annotations.Mutable
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.property.Property
import reactiveandroid.property.PropertyOf
import rx.Observable
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/17/15.
 */

class Action<T, U>(conditionEnabled: Property<Boolean>, private val execution: (T) -> Observable<U>) {

    constructor(execution: (T) -> Observable<U>) : this(PropertyOf(true), execution)

    private var _executing = MutablePropertyOf(false)
    public val executing: PropertyOf<Boolean> = PropertyOf(_executing)

    private var _enabled = MutablePropertyOf(false)
    public val enabled: PropertyOf<Boolean> = PropertyOf(_enabled)

    private var subject = PublishSubject.create<Observable<U>>()
    public var executions: Observable<Observable<U>> = subject

    public var name: String = ""

    private var output: Observable<U>? by Delegates.observable(null) { meta, oldValue: Observable<U>?, newValue: Observable<U>? ->
        subject.onNext(newValue)
    }

    init {
        _enabled.bind(Observable.combineLatest(conditionEnabled.observable, _executing.observable) { enabled, executing ->
            enabled && !executing
        })
    }

    public fun execute(input: T): Observable<U> {
        val subscriptions = CompositeSubscription()

        output = Observable.create<U> { subscriber ->
            val executionObservable = execution(input)

            var willExecute = false

            if (_enabled.value) {
                _executing.value = true
                willExecute = true
            }

            if (!willExecute) {
                subscriber.onError(IllegalStateException("$name is not enabled, executing : ${_executing.value}"))
                return@create
            }

            subscriptions += executionObservable.subscribe({ next ->
                subscriber.onNext(next)
            }, { error ->
                subscriber.onError(error)
                subscriptions.unsubscribe()
                _executing.value = false
            }, {
                subscriber.onCompleted()
                subscriptions.unsubscribe()
                _executing.value = false
            })
        }

        return output!!
    }

}


