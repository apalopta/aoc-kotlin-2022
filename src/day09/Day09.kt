package day09

import println
import readInput
import kotlin.math.abs

data class Pos(val x: Int, val y: Int) {
    operator fun plus(move: Move): Pos = Pos(this.x + move.dx, this.y + move.dy)
    operator fun minus(other: Pos): Move = Move(this.x - other.x, this.y - other.y)
}

enum class Direction(val move: Move) {
    U(Move(0, -1)),
    D(Move(0, 1)),
    R(Move(1, 0)),
    L(Move(-1, 0)),
}

data class Move(val dx: Int, val dy: Int)

val Move.distance: Int get() = maxOf(abs(dx), abs(dy))

fun tailToHeadAttraction(head: Pos, tail: Pos): Move =
    if ((head - tail).distance > 1) {
        Move((head.x - tail.x).coerceIn(-1, 1), (head.y - tail.y).coerceIn(-1, 1))
    } else {
        Move(0, 0)
    }

private fun calcTailPositions(moves: List<Pair<Direction, Int>>, numberOfKnots: Int): Int {
    val rope = MutableList(numberOfKnots) { Pos(0, 0) }
    val visitedByTail = mutableSetOf(rope.last())

    for ((d, n) in moves) {
        repeat(n) {
            rope[0] = rope[0] + d.move
            rope.indices.zipWithNext().forEach { (headIdx, tailIdx) ->
                rope[tailIdx] = rope[tailIdx] + tailToHeadAttraction(rope[headIdx], rope[tailIdx])
            }
            visitedByTail += rope.last()
        }
    }

    return visitedByTail.size
}

fun main() {
    val moves = readInput("day09/Day09").map { line ->
        line.split(" ").let { (dir, dis) -> Direction.valueOf(dir) to dis.toInt() }
    }
    // part1
    calcTailPositions(moves, 2).println()
    // part2
    calcTailPositions(moves, 10).println()
}
