package daylight.structure.algebraicStructure

// --- start ---
import daylight.structure.algebraicStructure.Monoid

abstract class Group<T> : Monoid<T>() {
    abstract fun inv(a: T): T
}