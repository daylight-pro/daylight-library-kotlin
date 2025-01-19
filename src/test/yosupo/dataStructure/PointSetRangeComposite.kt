package test.yosupo.dataStructure

// competitive-verifier: PROBLEM https://judge.yosupo.jp/problem/point_set_range_composite

import daylight.ModIntFactory
import daylight.ModIntFactory.ModInt
import daylight.Scanner
import daylight.structure.SegmentTree
import daylight.structure.algebraicStructure.Monoid
import java.io.PrintWriter

object PointSetRangeComposite {

    val mint = ModIntFactory(998244353)

    object M : Monoid<Pair<ModInt, ModInt>>() {
        override fun op(a: Pair<ModInt, ModInt>, b: Pair<ModInt, ModInt>): Pair<ModInt, ModInt> {
            return Pair(a.first * b.first, b.first * a.second + b.second)
        }

        override fun e(): Pair<ModInt, ModInt> {
            return Pair(mint.create(1), mint.create(0))
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun solve(sc: Scanner, out: PrintWriter) {
        val (N, Q) = sc.readInts()
        val V = MutableList<Pair<ModInt, ModInt>>(N) { M.e() }
        for (i in 0..<N) {
            val (a, b) = sc.readInts()
            V[i] = (Pair(mint.create(a), mint.create(b)))
        }
        val seg = SegmentTree(V, M)
        repeat(Q) {
            val row = sc.readInts()
            if (row[0] == 0) {
                val (k, p, c, d) = row
                seg.set(p, Pair(mint.create(c), mint.create(d)))
            } else {
                val (k, l, r, x) = row
                val (a, b) = seg.prod(l, r)
                out.println(a * mint.create(x) + b)
            }
        }
    }

    fun main() {
        val sc = Scanner()
        val out = PrintWriter(System.out)
        val t = 1
        // val t = sc.readInt()
        Thread(
            null,
            {
                repeat(t) { solve(sc, out) }
                out.flush()
            },
            "solve",
            128 * 1024 * 1024
        ).start()
    }
}