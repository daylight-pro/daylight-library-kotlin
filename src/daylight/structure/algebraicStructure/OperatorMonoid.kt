package daylight.structure.algebraicStructure

// --- start ---
import daylight.structure.algebraicStructure.Operator
import daylight.structure.algebraicStructure.Monoid

abstract class OperatorMonoid<T, U> : Monoid<T>(), Operator<U, T>