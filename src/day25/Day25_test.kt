package day25

import readInput

fun main() {
    val inputToDec = readInput("Day25_test_toDec").drop(1)
        .map { it.trim().split(" +".toRegex()) }
        .associate { (k, v) -> k to v.toLong() }
    testToDec(inputToDec)

    val inputToSnafu = readInput("Day25_test_toSnafu").drop(1)
        .map { it.trim().split(" +".toRegex()) }
        .associate { (k, v) -> k.trim().toLong() to v.trim() }
    testToSnafu(inputToSnafu)
}

private fun testToDec(input: Map<String, Long>) {
    input.forEach { (snafu, dec) ->
        val calc = snafu.toDec()
        if (calc != dec) error("fail '$calc' from '$snafu'")
    }
}

private fun testToSnafu(input: Map<Long,String>) {
    input.forEach { (dec, snafu) ->
        val calc = dec.toSnafu()
        if (calc != snafu) error("fail '$calc' to '$snafu'")
    }
}
