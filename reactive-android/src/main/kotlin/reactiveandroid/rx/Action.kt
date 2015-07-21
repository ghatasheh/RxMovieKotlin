package reactiveandroid.rx

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

    private val _executing = MutablePropertyOf(false)
    public val executing: PropertyOf<Boolean> = PropertyOf(_executing)

    private val _enabled = MutablePropertyOf(false)
    public val enabled: PropertyOf<Boolean> = PropertyOf(_enabled)

    private val executionSubject = PublishSubject.create<Observable<U>>()
    public val executions: Observable<Observable<U>> = executionSubject

    public var name: String = ""

    private var output: Observable<U>? by Delegates.observable(null) { _, __: Observable<U>?, newValue: Observable<U>? ->
        executionSubject.onNext(newValue)
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
                val error = IllegalStateException("$name is not enabled, executing : ${_executing.value}")
                subscriber.onError(error)
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


