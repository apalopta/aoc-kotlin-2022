import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

val nl = System.getProperty("line.separator")!!

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()


fun readSplitInput(name: String): List<List<String>> {
    return File("src", "$name.txt")
        .readText()
        .removeSuffix(nl)
        .split("$nl$nl")
        .map { it.split(nl).map { line -> line.trim() } }
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
