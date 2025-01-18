package daylight.structure

abstract class Monoid<T> {
    abstract fun op(a: T, b: T): T
    abstract fun e(): T
}