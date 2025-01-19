package test.yosupo.dataStructure

// competitive-verifier: PROBLEM https://judge.yosupo.jp/problem/range_affine_point_get

import daylight.ModIntFactory
import daylight.ModIntFactory.ModInt
import daylight.Scanner
import daylight.structure.LazySegmentTree
import daylight.structure.algebraicStructure.OperatorMonoid
import java.io.PrintWriter


object RangeAffinePointGet {
    val mint = ModIntFactory(998244353)

    object OM : OperatorMonoid<ModInt, Pair<ModInt, ModInt>>() {
        override fun op(a: ModInt, b: ModInt): ModInt {
            return a + b
        }

        override fun e(): ModInt {
            return mint.create(0)
        }

        override fun id(): Pair<ModInt, ModInt> {
            return Pair(mint.create(1), mint.create(0))
        }

        override fun composition(f: Pair<ModInt, ModInt>, g: Pair<ModInt, ModInt>): Pair<ModInt, ModInt> {
            return Pair(f.first * g.first, f.first * g.second + f.second)
        }

        override fun mapping(f: Pair<ModInt, ModInt>, x: ModInt): ModInt {
            return f.first * x + f.second
        }

    }

    @OptIn(ExperimentalStdlibApi::class)
    fun solve(sc: Scanner, out: PrintWriter) {
        val (N, Q) = sc.readInts()
        val A = sc.readInts()
        val V = MutableList<ModInt>(N) { OM.e() }
        for (i in 0..<N) {
            V[i] = mint.create(A[i])
        }
        val seg = LazySegmentTree(V, OM)
        repeat(Q) {
            val row = sc.readInts()
            if (row[0] == 0) {
                val (k, l, r, b, c) = row
                seg.apply(l, r, Pair(mint.create(b), mint.create(c)))
            } else {
                val (k, i) = row
                out.println(seg[i].value())
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