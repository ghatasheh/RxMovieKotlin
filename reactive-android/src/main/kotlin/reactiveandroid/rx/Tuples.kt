package reactiveandroid.rx

import java.io.Serializable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

public data class Tuple2<A, B>(
        public val first: A,
        public val second: B
) : Serializable, Component1<A>, Component2<B> {

    public override fun toString(): String = "($first, $second)"

}

public data class Tuple3<A, B, C>(
        public val first: A,
        public val second: B,
        public val third: C
) : Serializable, Component1<A>, Component2<B>, Component3<C> {

    public override fun toString(): String = "($first, $second, $third)"

}

public data class Tuple4<A, B, C, D>(
        public val first: A,
        public val second: B,
        public val third: C,
        public val fourth: D)
: Serializable, Component1<A>, Component2<B>, Component3<C>, Component4<D> {

    public override fun toString(): String = "($first, $second, $third, $fourth)"

}
