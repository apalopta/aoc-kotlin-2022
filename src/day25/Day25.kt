package day25

import println
import readInput
import kotlin.math.pow

fun main() {
    val input = readInput("Day25")

    // part1
    input.sumOf { line -> line.toDec() }.toSnafu().println()
}

infix fun Int.pow(i: Int) = toDouble().pow(i.toDouble()).toLong()

private fun Char.toSnafuDigit() = when (this) {
    '1' -> 1
    '2' -> 2
    '0' -> 0
    '-' -> -1
    '=' -> -2
    else -> error("unknown character for SNAFU: $this")
}

private fun Int.toSnafuChar() = when (this) {
    -2 -> '='
    -1 -> '-'
    2 -> '2'
    1 -> '1'
    0 -> '0'
    else -> error("unknown digit for SNAFU: $this")
}

private fun Int.toSnafuRemainder() = when (this) {
    4 -> -1
    3 -> -2
    2 -> 2
    1 -> 1
    0 -> 0
    else -> error("unknown remainder for SNAFU $this")
}

fun String.toDec(): Long =
    toCharArray().reversed().foldIndexed(0L) { exp, acc, char ->
        (acc + char.toSnafuDigit() * (5 pow exp))
    }

fun Long.toSnafu(): String {
    var value = this
    val charsReversed = mutableListOf<Char>()

    while (value > 0) {
        val current = (value % 5).toInt().toSnafuRemainder()
        charsReversed.add(current.toSnafuChar())
        // move the remainder to the next position
        value = (value + (if (current < 0) -current else 0)) / 5
    }

    return charsReversed.reversed().joinToString("")
}
