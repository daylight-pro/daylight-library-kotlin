package daylight.structure

// --- start ---

@OptIn(ExperimentalStdlibApi::class)
class Dsu(private val n: Int) {
    private var parentOrSize: IntArray = IntArray(n) { -1 }

    fun merge(a: Int, b: Int): Int {
        if (a !in 0..<n) throw IndexOutOfBoundsException("a=$a")
        if (b !in 0..<n) throw IndexOutOfBoundsException("b=$b")
        var x = leader(a)
        var y = leader(b)
        if (x == y) return x
        if (-parentOrSize[x] < -parentOrSize[y]) {
            x = y.also { y = x }
        }
        parentOrSize[x] += parentOrSize[y]
        parentOrSize[y] = x
        return x
    }

    fun same(a: Int, b: Int): Boolean {
        if (a !in 0..<n) throw IndexOutOfBoundsException("a=$a")
        if (b !in 0..<n) throw IndexOutOfBoundsException("b=$b")
        return leader(a) == leader(b)
    }

    fun size(a: Int): Int {
        if (a !in 0..<n) throw IndexOutOfBoundsException("a=$a")
        return parentOrSize[leader(a)]
    }

    fun leader(a: Int): Int {
        if (a !in 0..<n) throw IndexOutOfBoundsException("a=$a")
        return if (parentOrSize[a] < 0) a
        else {
            parentOrSize[a] = leader(parentOrSize[a])
            parentOrSize[a]
        }
    }

    fun groups(a: Int): List<List<Int>> {
        val leaderBuf = IntArray(n)
        val groupSize = IntArray(n)
        for (i in 0..<n) {
            leaderBuf[i] = leader(i)
            groupSize[leaderBuf[i]]++
        }
        val result = ArrayList<ArrayList<Int>>()
        for (i in 0..<n) {
            result.add(ArrayList(groupSize[i]))
        }
        for (i in 0..<n) {
            result[leaderBuf[i]].add(i)
        }
        result.removeIf { v -> v.isEmpty() }
        return result
    }

    fun count(): Int {
        return n
    }
}