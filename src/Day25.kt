import kotlin.math.pow

fun main() {
    val input = readInput("Day25")

    part1(input).println()
}

private fun part1(input: List<String>): String {
    return input.sumOf { line -> line.toDec() }.toSnafu()
}

infix fun Int.pow(i: Int) = toDouble().pow(i.toDouble()).toLong()

val snafuToDecMap = mapOf(
    '1' to 1,
    '2' to 2,
    '0' to 0,
    '-' to -1,
    '=' to -2
)

val decToSnafuMap = mapOf(
    -2 to "=",
    -1 to "-",
    2 to "2",
    1 to "1",
    0 to "0",
)

fun String.toDec(): Long =
    toCharArray().reversed()
        .foldIndexed(0L) { exp, acc, char ->
            if (char !in snafuToDecMap.keys) {
                error("unknown snafu $char in $this")
            }
            (acc + snafuToDecMap[char]!! * (5 pow exp))
        }

fun Long.toSnafu(): String {
    var value = this
    var charsReversed = ""
    while (value > 0) {
        val current = (value % 5).toInt()
        val remainder = (if (current > 2) (current - 5) else 0).toInt()
        charsReversed += if (current > 2) decToSnafuMap[remainder] else decToSnafuMap[current]
        val trans = (if (current > 2) -remainder else 0)
        value = ((value + trans) / 5)
    }
    return charsReversed.reversed()
}
