package daylight

// --- start ---
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