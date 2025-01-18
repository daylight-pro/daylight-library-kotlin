package test.yosupo.dataStructure

// competitive-verifier: PROBLEM https://judge.yosupo.jp/problem/static_range_sum

import daylight.Scanner
import daylight.structure.FenwickTree
import java.io.PrintWriter

object StaticRangeSum {
    @OptIn(ExperimentalStdlibApi::class)
    private fun solve(sc: Scanner, out: PrintWriter) {
        val (N, Q) = sc.readInts()
        val A = sc.readLongs()
        val fw = FenwickTree(N)
        for (i in 0..<N) {
            fw.add(i, A[i])
        }
        repeat(Q) {
            val (l, r) = sc.readInts()
            out.println(fw.sum(l, r))
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