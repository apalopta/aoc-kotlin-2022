import java.io.File

fun main() {

    val input = getElvesProperties()
    val sortedCaloriesDescending = input.map { it.sum() }.sortedDescending()

    // part 1
    sortedCaloriesDescending.sumOfTopN(1).println()
    // part 2
    sortedCaloriesDescending.sumOfTopN(3).println()
}

private fun List<Int>.sumOfTopN(n: Int): Int = take(n).sum()

/** Returns the lists of calories per elf. */
private fun getElvesProperties(): List<List<Int>> {
    val nl = System.getProperty("line.separator")
    val input = File("src/Day01.txt")
        .readText()
        .removeSuffix(nl)
        .split("$nl$nl")
        .map { properties -> properties.split(nl)
            .map { property -> property.toInt() }
        }
    return input
}
