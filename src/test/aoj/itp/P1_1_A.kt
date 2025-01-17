package test.aoj.itp

import daylight.Scanner
import java.io.PrintWriter

// competitive-verifier: PROBLEM https://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=ITP1_1_A&lang=ja

@OptIn(ExperimentalStdlibApi::class)
object P1_1_A {
    private fun solve(sc: Scanner, out: PrintWriter) {
        out.println("Hello World")
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