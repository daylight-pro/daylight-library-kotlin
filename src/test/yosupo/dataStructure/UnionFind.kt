package test.yosupo.dataStructure

import daylight.Scanner
import acl.DSU
import java.io.PrintWriter

// competitive-verifier: PROBLEM https://judge.yosupo.jp/problem/unionfind

@OptIn(ExperimentalStdlibApi::class)
class UnionFind {
    companion object {
        private fun solve(sc: Scanner, out: PrintWriter) {
            val (N, Q) = sc.readInts()
            val dsu = DSU(N)
            repeat(Q) {
                val (t, u, v) = sc.readInts()
                if (t == 0) {
                    dsu.merge(u, v)
                } else {
                    out.println(
                        if (dsu.same(u, v)) {
                            1
                        } else {
                            0
                        }
                    )
                }
            }
        }

        fun main() {
            val sc = Scanner()
            val out = PrintWriter(System.out)
            val t = 1
            //     val t = sc.readInt()
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
}