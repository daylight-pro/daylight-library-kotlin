import java.util.*
import kotlin.collections.*
import java.io.PrintWriter
class Scanner {
    private val br = System.`in`.bufferedReader()

    fun readInt(): Int {
        return br.readLine().toInt()
    }

    fun readInts(): IntArray {
        return br.readLine().split(' ').map { it.toInt() }.toIntArray()
    }

    fun readToken(): String {
        return br.readLine()
    }

    fun readTokens(): List<String> {
        return br.readLine().split(' ')
    }

    fun readDouble(): Double {
        return br.readLine().toDouble()
    }

    fun readDoubles(): DoubleArray {
        return br.readLine().split(' ').map { it.toDouble() }.toDoubleArray()
    }

    fun readLong(): Long {
        return br.readLine().toLong()
    }

    fun readLongs(): LongArray {
        return br.readLine().split(' ').map { it.toLong() }.toLongArray()
    }

    fun readChar(): Char {
        return br.readLine()[0]
    }

    fun readChars(): CharArray {
        return br.readLine().split(' ').map { it[0] }.toCharArray()
    }
}
@ExperimentalStdlibApi
fun solve(sc: Scanner, out: PrintWriter) {

}

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val sc = Scanner()
    val out = PrintWriter(System.out)
    val t = 1
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
