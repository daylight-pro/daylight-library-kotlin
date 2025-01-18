package daylight.structure

// --- start ---

@OptIn(ExperimentalStdlibApi::class)
class FenwickTree(private val n: Int) {
    private val data = LongArray(n)

    fun add(p: Int, x: Long) {
        if (p !in 0..<n) throw IndexOutOfBoundsException("p:$p")
        var p = p + 1;
        while (p <= n) {
            data[p - 1] += x
            p += p and -p
        }
    }

    private fun sum(r: Int): Long {
        var s = 0L
        var r = r
        while (r > 0) {
            s += data[r - 1]
            r -= r and -r
        }
        return s
    }

    fun sum(l: Int, r: Int): Long {
        if (l !in 0..n) throw IndexOutOfBoundsException("l:$l")
        if (r !in 0..n) throw IndexOutOfBoundsException("r:$r")
        if (l > r) throw IllegalArgumentException("l > r")
        return sum(r) - sum(l)
    }

    fun get(i: Int): Long {
        if (i !in 0..<n) throw IndexOutOfBoundsException("i:$i")
        return sum(i + 1) - sum(i)
    }
}