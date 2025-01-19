package daylight.structure.algebraicStructure

// --- start ---
import daylight.structure.algebraicStructure.Semigroup

abstract class Monoid<T> : Semigroup<T>() {
    abstract fun e(): T
}