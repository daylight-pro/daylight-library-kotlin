package test.yosupo.dataStructure

// competitive-verifier: PROBLEM https://judge.yosupo.jp/problem/range_affine_range_sum

import daylight.ModIntFactory
import daylight.ModIntFactory.ModInt
import daylight.Scanner
import daylight.structure.algebraicStructure.OperatorMonoid
import java.io.PrintWriter

object RangeAffineRangeSum {

    val mint = ModIntFactory(998244353)

    data class S(val value: ModInt, val size: Int)

    @OptIn(ExperimentalStdlibApi::class)
    private fun solve(sc: Scanner, out: PrintWriter) {
        val (N, Q) = sc.readInts()
        val A = sc.readInts()
        val V = MutableList(N) { S(mint.create(1), 0) }
        for (i in 0..<N) {
            V[i] = S(mint.create(A[i]), 1)
        }
        val seg = daylight.structure.LazySegmentTree(V, object : OperatorMonoid<S, Pair<ModInt, ModInt>>() {
            override fun op(a: S, b: S): S {
                return S(a.value + b.value, a.size + b.size)
            }

            override fun e(): S {
                return S(mint.create(0), 0)
            }

            override fun id(): Pair<ModInt, ModInt> {
                return Pair(mint.create(1), mint.create(0))
            }

            override fun composition(f: Pair<ModInt, ModInt>, g: Pair<ModInt, ModInt>): Pair<ModInt, ModInt> {
                return Pair(f.first * g.first, f.first * g.second + f.second)
            }

            override fun mapping(f: Pair<ModInt, ModInt>, x: S): S {
                return S(f.first * x.value + f.second * x.size, x.size)
            }
        })
        repeat(Q) {
            val row = sc.readInts()
            if (row[0] == 0) {
                val (k, l, r, b, c) = row
                seg.apply(l, r, Pair(mint.create(b), mint.create(c)))
            } else {
                val (k, l, r) = row
                val list = List<Int>(N) { i -> seg[i].value.value() }
                out.println(seg.prod(l, r).value)
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