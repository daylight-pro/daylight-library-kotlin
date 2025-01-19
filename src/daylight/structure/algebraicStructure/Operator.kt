package daylight.structure.algebraicStructure

// --- start ---
interface Operator<T, U> {
    fun mapping(f: T, x: U): U
    fun composition(f: T, g: T): T
    fun id(): T
}
