package daylight.structure

class SegmentTree<T>(v: List<T>, private val monoid: Monoid<T>) {
    private val n = v.count()
    private val log: Int
    private val size: Int
    private val d: MutableList<T>

    init {
        var lg = 0
        while ((1u shl lg) < n.toUInt()) {
            lg++
        }
        log = lg
        size = 1 shl log
        d = MutableList<T>(2 * size) { monoid.e() }
        for (i in 0..<n) {
            d[size + i] = v[i]
        }
        for (i in size - 1 downTo 1) {
            update(i)
        }
    }

    constructor(n: Int, monoid: Monoid<T>) : this(List(n) { monoid.e() }, monoid)

    private fun update(i: Int) {
        d[i] = monoid.op(d[2 * i], d[2 * i + 1])
    }

    fun set(p: Int, x: T) {
        if (p !in 0..<n) throw IndexOutOfBoundsException("p=$p")
        val p = p + size
        d[p] = x
        for (i in 1..log) {
            update(p shr i)
        }
    }

    fun get(p: Int): T {
        if (p !in 0..<n) throw IndexOutOfBoundsException("p=$p")
        return d[p + size]
    }

    fun prod(l: Int, r: Int): T {
        if (l !in 0..r) throw IllegalArgumentException("l=$l")
        if (r !in 0..n) throw IndexOutOfBoundsException("r=$r")
        if (l > r) throw IllegalArgumentException("l > r")
        var sml = monoid.e()
        var smr = monoid.e()
        var l = l + size
        var r = r + size
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

    fun maxRight(l: Int, f: (T) -> Boolean): Int {
        if (l !in 0..n) throw IndexOutOfBoundsException("l=$l")
        if (!f(monoid.e())) throw IllegalArgumentException("f must be true")
        if (l == n) return n
        var l = l + size
        var sm = monoid.e()
        do {
            while (l % 2 == 0) {
                l = l shr 1
            }
            if (!f(monoid.op(sm, d[l]))) {
                while (l < size) {
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
        if (!f(monoid.e())) throw IllegalArgumentException("f must be true")
        if (r == 0) return 0
        var r = r + size
        var sm = monoid.e()
        do {
            r--
            while (r > 1 && r % 2 != 0) {
                r = r shr 1
            }
            if (!f(monoid.op(d[r], sm))) {
                while (r < size) {
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