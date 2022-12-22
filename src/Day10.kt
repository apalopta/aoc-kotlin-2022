fun main() {
    fun calculateCycles(input: List<String>): IntArray {
        val noops = input.count { it == "noop" }
        val addxs = input.count { it.startsWith("addx") }
        // add the 0 cycle since we count from 1
        val cycles = IntArray(2 * noops + 3 * addxs + 1)
        cycles[0] = 1
        // the first cycle always keeps to be 1
        cycles[1] = 1
        // start at index 1: no extra checks for out-of-bounds then
        var i = 1
        for (line in input) {
            when {
                line.startsWith("noop") -> {
                    cycles[i + 1] = cycles[i]
                    i += 1
                }
                line.startsWith("addx") -> {
                    val number = line.substringAfter(" ").toInt()
                    cycles[i + 1] = cycles[i]
                    cycles[i + 2] = cycles[i + 1] + number
                    i += 2

                }
                else -> error("unknown instruction $line")
            }
        }

        return cycles
    }

    fun part1(cycles: IntArray ): Int = listOf(20, 60, 100, 140, 180, 220).sumOf { cycles[it] * it }

    fun part2(cycles: IntArray): String {

        if (cycles.size != 240) { error("input has wrong size, expected 240") }

        val rows = 6
        val width = 40
        val sb = buildString {
            repeat(rows) { row ->
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
    part2(cycles.copyOfRange(1, cycles.size)).println()
}
