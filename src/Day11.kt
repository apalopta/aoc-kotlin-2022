import java.io.File

val nl = System.getProperty("line.separator")!!

//val monkeys = mutableListOf<Monkey>()

data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisor: Int,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var totalChecks: Int = 0
) {
    fun receive(item: Long) {
        items.add(item)
    }

    fun round(monkeys: List<Monkey>, worryLevelDivisor: Int, commonDiv: Int) {
        while (items.isNotEmpty()) {
            totalChecks++
            val item = operation(items.removeFirst())
            val newItem = (item / worryLevelDivisor.toLong()) % commonDiv
            if (newItem % divisor == 0.toLong())
                monkeys[trueMonkey].receive(newItem)
            else
                monkeys[falseMonkey].receive(newItem)
        }
    }
}

fun main() {

    val input = readSplitInput()

    part1(input).println()

    part2(input).println()
}

private fun part1(input: List<List<String>>): Long {
    val (monkeys, commonDiv) = initMonkeys(input)
    return doMonkeyRounds(monkeys.toList(), 20, 3, commonDiv)
}

private fun part2(input: List<List<String>>) {
    val (monkeys, commonDiv) = initMonkeys(input)
    doMonkeyRounds(monkeys.toList(), 10000, 1, commonDiv).println()
}

private fun readSplitInput(): List<List<String>> {
    return File("src", "Day11.txt")
        .readText()
        .removeSuffix(nl)
        .split("$nl$nl")
        .map { it.split(nl).map { line -> line.trim() } }
}

fun initMonkeys(input: List<List<String>>): Pair<MutableList<Monkey>, Int> {
    val monkeys = mutableListOf<Monkey>()
    input.forEachIndexed { i, monkeyDef ->
        monkeys.add(
            Monkey(
                i,
                monkeyDef[1].substringAfter(": ").split(',').map { it.trim().toLong() }.toMutableList(),
                monkeyDef[2].parseOperation(),
                monkeyDef[3].substringAfter("by ").trim().toInt(),
                monkeyDef[4].substringAfter("monkey ").trim().toInt(),
                monkeyDef[5].substringAfter("monkey ").trim().toInt(),
            )
        )
    }
    val commonDiv = monkeys.map { it.divisor }.reduce { a, b -> a * b }


    return monkeys to commonDiv
}

private fun doMonkeyRounds(monkeys: List<Monkey>, rounds: Int, worryLevelDivisor: Int, commonMult: Int): Long {
    repeat(rounds) { _ ->
        monkeys.forEach { monkey ->
            monkey.round(monkeys, worryLevelDivisor, commonMult)
        }
    }

    val (m1, m2) = monkeys.sortedByDescending { it.totalChecks }.toMutableList().take(2)

    return (m1.totalChecks.toLong() * m2.totalChecks.toLong())
}

fun String.parseOperation(): (Long) -> Long {
    val (operator, value) = substringAfter("old ").split(' ')
    return when (operator) {
        "*" -> { it -> it * (if (value == "old") it else value.trim().toLong()) }
        "+" -> { it -> it + (if (value == "old") it else value.trim().toLong()) }
        else -> error("unknown Operation $this")
    }
}
