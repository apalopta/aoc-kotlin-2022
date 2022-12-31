package day20

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
//        println(input)
        val inputPairs = input.map { it.toInt() to false }.toMutableList()
        println(inputPairs)
        val working = input.toMutableList()
        println(working)
        val length = input.size
//        println(inputAsInts.max())
//        println(inputAsInts.min())

        while (inputPairs.any { !it.second }) {
            val i = inputPairs.indexOfFirst { !it.second }
//            println("next from i $i")
            val moveBy = inputPairs[i].first
            val lastIndex = inputPairs.lastIndex

            val idx = i + moveBy

            val newIndex =
                if (moveBy >= 0) {
                    if (idx > lastIndex) {
                        (idx - lastIndex) % length
                    } else {
                        idx
                    }
                } else {
                    if (idx <= 0) {
                        (idx + 2*length - 1) % length
                    } else {
                        (idx + 2*length) % length
                    }
                }
//            val newIndex = if (moveBy >= 0) (i + moveBy) % length else (i + moveBy + length - 1) % length
//            println("value: $moveBy newIndex: $newIndex")

            val value = inputPairs.removeAt(i)
//            println(inputPairs)
            inputPairs.add(newIndex, value.copy(second = true))
//            println(inputPairs)
            println(inputPairs.map { it.first })
            println("---")
        }

        val idxOfZero =  working.indexOf("0")
        println(idxOfZero)
        val numbersOfInterest = listOf(1000, 2000, 3000).map { working[(idxOfZero + it) % length].toInt() }
        println(numbersOfInterest)
        val sum = numbersOfInterest.sumOf {it}
        println(sum)
        println("---")



        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day20_test")
    part1(input).println()
    part2(input).println()
}
