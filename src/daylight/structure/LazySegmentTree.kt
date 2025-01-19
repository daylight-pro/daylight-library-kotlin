package daylight.structure

// --- start ---
import daylight.structure.algebraicStructure.OperatorMonoid

class LazySegmentTree<T, U>(v: List<T>, private val monoid: OperatorMonoid<T, U>) {
    private val n = v.size
    private val log: Int
    private val size: Int
    private val d: MutableList<T>
    private val lz: MutableList<U>

    init {
        var lg = 0
        while ((1u shl lg) < n.toUInt()) {
            lg++
        }
        log = lg
        size = 1 shl log
        d = MutableList<T>(2 * size) { monoid.e() }
        lz = MutableList<U>(size) { monoid.id() }
        for (i in 0..<n) {
            d[size + i] = v[i]
        }
        for (i in size - 1 downTo 1) {
            update(i)
        }
    }

    constructor(n: Int, monoid: OperatorMonoid<T, U>) : this(List(n) { monoid.e() }, monoid)

    private fun update(i: Int) {
        d[i] = monoid.op(d[2 * i], d[2 * i + 1])
    }

    private fun allApply(i: Int, f: U) {
        d[i] = monoid.mapping(f, d[i])
        if (i < size) {
            lz[i] = monoid.composition(f, lz[i])
        }
    }

    private fun push(i: Int) {
        allApply(2 * i, lz[i])
        allApply(2 * i + 1, lz[i])
        lz[i] = monoid.id()
    }

    operator fun set(p: Int, x: T) {
        if (p !in 0..<n) throw IndexOutOfBoundsException("p=$p")
        val p = p + size
        for (i in log downTo 1) {
            push(p shr i)
        }
        d[p] = x
        for (i in 1..log) {
            update(p shr i)
        }
    }

    operator fun get(p: Int): T {
        if (p !in 0..<n) throw IndexOutOfBoundsException("p=$p")
        val p = p + size
        for (i in log downTo 1) {
            push(p shr i)
        }
        return d[p]
    }

    fun prod(l: Int, r: Int): T {
        if (l !in 0..r) throw IllegalArgumentException("l=$l,r=$r")
        if (r !in 0..n) throw IndexOutOfBoundsException("r=$r")
        if (l == r) return monoid.e()
        var l = l + size
        var r = r + size

        for (i in log downTo 1) {
            if ((l shr i) shl i != l) push(l shr i)
            if ((r shr i) shl i != r) push((r - 1) shr i)
        }

        var sml = monoid.e()
        var smr = monoid.e()

        while (l < r) {
            if (l and 1 != 0) {
                sml = monoid.op(sml, d[l++])
            }
            if (r and 1 != 0) {
                smr = monoid.op(d[--r], smr)
            }
            l = l shr 1
            r = r shr 1
        }
        return monoid.op(sml, smr)
    }

    fun allProd(): T {
        return d[1]
    }

    fun apply(p: Int, f: U) {
        if (p !in 0..<n) throw IndexOutOfBoundsException("p=$p")
        val p = p + size
        for (i in log downTo 1) {
            push(p shr i)
        }
        d[p] = monoid.mapping(f, d[p])
        for (i in 1..log) {
            update(p shr i)
        }
    }

    fun apply(l: Int, r: Int, f: U) {
        if (l !in 0..r) throw IllegalArgumentException("l=$l,r=$r")
        if (r !in 0..n) throw IndexOutOfBoundsException("r=$r")
        if (l == r) return
        var l = l + size
        var r = r + size
        for (i in log downTo 1) {
            if ((l shr i) shl i != l) push(l shr i)
            if ((r shr i) shl i != r) push((r - 1) shr i)
        }
        var l2 = l
        var r2 = r
        while (l < r) {
            if (l and 1 != 0) allApply(l++, f)
            if (r and 1 != 0) allApply(--r, f)
            l = l shr 1
            r = r shr 1
        }
        l = l2
        r = r2
        for (i in 1..log) {
            if ((l shr i) shl i != l) update(l shr i)
            if ((r shr i) shl i != r) update((r - 1) shr i)
        }
    }

    fun maxRight(l: Int, f: (T) -> Boolean): Int {
        if (l !in 0..n) throw IndexOutOfBoundsException("l=$l")
        if (!f(monoid.e())) throw IllegalArgumentException("f(e) must be true")
        if (l == n) return n
        var l = l + size
        for (i in log downTo 1) {
            push(l shr i)
        }
        var sm = monoid.e()
        do {
            while (l % 2 == 0) {
                l = l shr 1
            }
            if (!f(monoid.op(sm, d[l]))) {
                while (l < size) {
                    push(l)
                    l = l shl 1
                    if (f(monoid.op(sm, d[l]))) {
                        sm = monoid.op(sm, d[l])
                        l++
                    }
                }
                return l - size
            }
            sm = monoid.op(sm, d[l])
            l++
        } while (l and -l != l)
        return n
    }

    fun minLeft(r: Int, f: (T) -> Boolean): Int {
        if (r !in 0..n) throw IndexOutOfBoundsException("r=$r")
        if (!f(monoid.e())) throw IllegalArgumentException("f(e) must be true")
        if (r == 0) return 0
        var r = r + size
        for (i in log downTo 1) {
            push(r - 1 shr i)
        }
        var sm = monoid.e()
        do {
            r--
            while (r > 1 && r % 2 != 0) {
                r = r shr 1
            }
            if (!f(monoid.op(d[r], sm))) {
                while (r < size) {
                    push(r)
                    r = r shl 1 or 1
                    if (f(monoid.op(d[r], sm))) {
                        sm = monoid.op(d[r], sm)
                        r--
                    }
                }
                return r + 1 - size
            }
            sm = monoid.op(d[r], sm)
        } while (r and -r != r)
        return 0
    }
}