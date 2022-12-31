package day11

import println
import readSplitInput

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
    val input = readSplitInput("Day11")

    var monkeys = initMonkeys(input)
    val commonDiv = monkeys.map { it.divisor }.reduce { a, b -> a * b }
    // part1
    doMonkeyRounds(monkeys, commonDiv, 3, 20).println()
    // part2
    monkeys = initMonkeys(input)
    doMonkeyRounds(monkeys, commonDiv, 1, 10000).println()
}

private fun initMonkeys(input: List<List<String>>): List<Monkey> {
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

    return monkeys.toList()
}

private fun doMonkeyRounds(monkeys: List<Monkey>, commonDiv: Int, worryLevelDivisor: Int, rounds: Int): Long {
    repeat(rounds) { _ ->
        monkeys.forEach { monkey ->
            monkey.round(monkeys, worryLevelDivisor, commonDiv)
        }
    }

    val (m1, m2) = monkeys.sortedByDescending { it.totalChecks }.toMutableList().take(2)

    return (m1.totalChecks.toLong() * m2.totalChecks.toLong())
}

private fun String.parseOperation(): (Long) -> Long {
    val (operator, value) = substringAfter("old ").split(' ')
    return when (operator) {
        "*" -> { it -> it * (if (value == "old") it else value.trim().toLong()) }
        "+" -> { it -> it + (if (value == "old") it else value.trim().toLong()) }
        else -> error("unknown Operation $this")
    }
}
