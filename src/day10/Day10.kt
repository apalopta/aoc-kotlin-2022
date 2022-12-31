package day10

import println
import readInput

fun main() {
    fun calculateCycles(input: List<String>): IntArray {
        val noops = input.count { it == "noop" }
        val addxs = input.count { it.startsWith("addx") }
        // add the 0 cycle since we count from 1 - and add a last empty element
        val cycles = IntArray(noops + 2 * addxs + 2)
        cycles[0] = 1
        // any cmd does nothing in the first cycle: always stays 1
        cycles[1] = 1
        // effective start of execution
        var i = 2
        for (line in input) {
            when {
                line.startsWith("noop") -> {
                    cycles[i] = cycles[i - 1]
                    i += 1
                }
                line.startsWith("addx") -> {
                    val number = line.substringAfter(" ").toInt()
                    cycles[i] = cycles[i - 1]
                    cycles[i + 1] = cycles[i] + number
                    i += 2
                }
                else -> error("unknown instruction $line")
            }
        }

        return cycles
    }

    fun part1(cycles: IntArray): Int = listOf(20, 60, 100, 140, 180, 220).sumOf { cycles[it] * it }

    fun part2a(cycles: IntArray, lines: Int, width: Int) {
        repeat(lines) { row ->
            repeat(width) { pixelIdx ->
                val cycle = row * width + pixelIdx
                val spritePos = cycles[cycle]
                val char = if (pixelIdx in (spritePos - 1..spritePos + 1)) '#' else '.'
                print(char)
            }
            println()
        }
    }

    fun part2b(cycles: IntArray, lines: Int, width: Int): String {
        val sb = buildString {
            repeat(lines) { row ->
                repeat(width) { pixelIdx ->
                    val cycle = row * width + pixelIdx
                    val spritePos = cycles[cycle]
                    val char = if (pixelIdx in (spritePos - 1..spritePos + 1)) '#' else '.'
                    append(char)
                }
                append(System.getProperty("line.separator"))
            }
        }

        return sb
    }

    val input = readInput("Day10")

    val cycles = calculateCycles(input)

    part1(cycles).println()
    // get rid of the 0 cycle for this
    part2a(cycles.copyOfRange(1, cycles.size), 6, 40).println()
    part2b(cycles.copyOfRange(1, cycles.size), 6, 40).println()
}
