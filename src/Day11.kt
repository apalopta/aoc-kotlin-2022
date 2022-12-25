import java.io.File

val nl = System.getProperty("line.separator")!!

val monkeys = mutableListOf<Monkey>()

data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisor: Long,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var totalChecks: Int = 0
) {
    fun receive(item: Long) {
        items.add(item)
    }

    fun round(monkeys: List<Monkey>, worryLevelDivisor: Int = 3) {
        val newValues = items.map { operation(it) }
        items.clear()
        items.addAll(newValues)
        while (items.isNotEmpty()) {
            totalChecks++
            val item = items.removeAt(0) / worryLevelDivisor
            val receiver = if (item % divisor == 0.toLong()) monkeys[trueMonkey] else monkeys[falseMonkey]
            receiver.receive(item)
        }
    }
}

fun main() {

    val input = readSplitInput()

    part1(input).println()
}

private fun readSplitInput(): List<List<String>> {
    return File("src", "Day11_test.txt")
        .readText()
        .removeSuffix(nl)
        .split("$nl$nl")
        .map { it.split(nl).map { line -> line.trim() } }
}

fun part1(input: List<List<String>>): Int {
    input.forEachIndexed { i, monkeyDef ->
        monkeys.add(
            Monkey(
                i,
                monkeyDef[1].substringAfter(": ").split(',').map { it.trim().toLong() }.toMutableList(),
                monkeyDef[2].parseOperation(),
                monkeyDef[3].substringAfter("by ").trim().toLong(),
                monkeyDef[4].substringAfter("monkey ").trim().toInt(),
                monkeyDef[5].substringAfter("monkey ").trim().toInt(),
            )
        )
    }

    monkeys.forEachIndexed { i, monkey ->
        println("Monkey $i: ${monkey.items}")
    }
    repeat(20) { round ->
        monkeys.forEachIndexed { i, monkey ->
            monkey.round(monkeys)
        }
        println("Round ${round + 1}")
        monkeys.forEachIndexed { i, monkey ->
            println("Monkey $i: ${monkey.items}")
        }
        monkeys.forEach {
            println("Monkey ${it.id} inspected ${it.totalChecks} items")
        }
    }

    val top2Monkeys = monkeys.sortedByDescending { it.totalChecks }.toMutableList().take(2)
    top2Monkeys.println()

    return top2Monkeys[0].totalChecks * top2Monkeys[1].totalChecks
}

fun String.parseOperation(): (Long) -> Long {
    val (operator, value) = substringAfter("old ").split(' ')
    return when (operator) {
        "*" -> if (value == "old") { it -> it * it } else { it -> it * value.trim().toLong() }
        "+" -> if (value == "old") { it -> it + it } else { it -> it + value.trim().toLong() }
        else -> error("unknown Operation $this")
    }
}
