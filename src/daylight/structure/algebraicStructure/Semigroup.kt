package daylight.structure.algebraicStructure

// --- start ---

abstract class Semigroup<T> {
    abstract fun op(a: T, b: T): T
}