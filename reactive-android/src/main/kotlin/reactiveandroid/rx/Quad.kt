package reactiveandroid.rx

import java.io.Serializable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

public data class Quad<out A, out B, out C, out D>(
        public val first: A,
        public val second: B,
        public val third: C,
        public val fourth: D)
: Serializable {

    public override fun toString(): String = "($first, $second, $third, $fourth)"

}
