open class File(val name: String, open val size: Long)

class Dir(name: String, val parentDir: Dir?) : File(name, 0) {
    val files: MutableList<File> = mutableListOf()
    override val size
        get() = files.sumOf { it.size }
}

fun main() {
    fun addDir(line: String, currentDir: Dir) {
        val (_, token2) = line.split(' ')
        val dir = Dir(token2, currentDir)
        currentDir.files.add(dir)
    }

    fun addFile(line: String, currentDir: Dir) {
        val (token1, token2) = line.split(' ')
        val file = File(token2, token1.toLong())
        currentDir.files.add(file)
    }

    fun changeDir(line: String, currentDir: Dir): Dir {
        val dirName = line.split(' ')[2]
        return currentDir.files.filterIsInstance<Dir>().find { it.name == dirName } ?: currentDir
    }

    fun walkDirs(dir: Dir): List<Dir> {
        val dirs = mutableListOf<Dir>()

        dir.files.filterIsInstance<Dir>()
            .forEach { directory ->
                dirs.addAll(walkDirs(directory))
            }

        return dirs
    }

    fun part1(input: List<String>): Int {
        val root = Dir("/", null)

        var currentDir = root
        input.forEach { line ->
            when {
                line == "$ cd /" -> currentDir = root
                line == "$ cd .." -> currentDir = currentDir.parentDir ?: root
                line.startsWith("$ cd") -> currentDir = changeDir(line, currentDir)
                line == "$ ls" -> return@forEach
                line.startsWith("dir") -> addDir(line, currentDir)
                line[0].isDigit() -> addFile(line, currentDir)
                else -> error("unknown command '$line'")
            }
        }

        val dirsOfInterest = walkDirs(root).filter { it.size <= 100000 }
        dirsOfInterest.forEach { println("${it.name} -> ${it.size}") }
        println(dirsOfInterest.sumOf { it.size })

        return input.size
    }

//    fun part2(input: List<String>): Int {
//        return input.size
//    }
//
//    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day07")
    input.subList(0, 3).forEach { println(it) }
    part1(input).println()
//    part2(input).println()
}
