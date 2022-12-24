package day9

import common.inputFor
import kotlin.math.abs

typealias Move = Pair<Int, Int>

val moves: Map<String, Move> = mapOf(
    "L" to (0 to -1),
    "R" to (0 to 1),
    "U" to (-1 to 0),
    "D" to (1 to 0),
)

typealias Place = Pair<Int, Int>

fun Place.move(m: Move): Place {
    return this.first + m.first to this.second + m.second
}

fun dirFrom(here: Int, there: Int): Int {
    return when {
        here < there -> 1
        here > there -> -1
        else -> 0
    }
}

fun Place.follow(lead: Place): Place {
    if (abs(this.first - lead.first) < 2 && abs(this.second - lead.second) < 2) {
        return this
    }
    return this.move(dirFrom(this.first, lead.first) to dirFrom(this.second, lead.second))
}

fun part1() {
    var head: Place = 0 to 0
    var tail: Place = 0 to 0
    val allTails = mutableSetOf<Place>()
    inputFor(9).forEachLine {
        val (dir, nStr) = it.split(" ")
        val n = nStr.toInt()
        val mv = moves[dir] ?: throw IllegalArgumentException("unknown move $dir")
        repeat(n) {
            head = head.move(mv)
            tail = tail.follow(head)
            allTails.add(tail)
        }
    }
    println(allTails.size)
}

fun part2() {
    var rope: Array<Place> = Array(10) { 0 to 0 }
    val allTails = mutableSetOf<Place>()
    inputFor(9).forEachLine {
        val (dir, nStr) = it.split(" ")
        val n = nStr.toInt()
        val mv = moves[dir] ?: throw IllegalArgumentException("unknown move $dir")
        repeat(n) {
            rope[0] = rope[0].move(mv)
            for (idx in 1 until rope.size) {
                rope[idx] = rope[idx].follow(rope[idx-1])
            }
            allTails.add(rope.last())
        }
    }
    println(allTails.size)
}

fun main() {
    part2()
}