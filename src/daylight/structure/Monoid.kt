package daylight.structure

// --- start ---

abstract class Monoid<T> {
    abstract fun op(a: T, b: T): T
    abstract fun e(): T
}