package test.yosupo.dataStructure

import daylight.Dsu
import daylight.Scanner
import java.io.PrintWriter

object UnionFind {
    private fun solve(sc: Scanner, out: PrintWriter) {
        val (N, Q) = sc.readInts()
        val dsu = Dsu(N)
        repeat(Q) {
            val (t, u, v) = sc.readInts()
            when (t) {
                0 -> dsu.merge(u, v)
                1 -> println(
                    if (dsu.same(u, v)) 1
                    else 0
                )
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