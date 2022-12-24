package day2

import common.inputFor

val vsPoints = mapOf(
    "A" to mapOf("R" to 3, "P" to 6, "S" to 0),
    "B" to mapOf("R" to 0, "P" to 3, "S" to 6),
    "C" to mapOf("R" to 6, "P" to 0, "S" to 3),
)

val response = mapOf(
    "A" to mapOf("X" to "S", "Y" to "R", "Z" to "P"),
    "B" to mapOf("X" to "R", "Y" to "P", "Z" to "S"),
    "C" to mapOf("X" to "P", "Y" to "S", "Z" to "R"),
)

val moveVal = mapOf(
    "R" to 1, "P" to 2, "S" to 3
)

fun main() {
    var score = 0
    inputFor(2).forEachLine {
        val moves = it.split(" ")
        val yourMove = response[moves[0]]!![moves[1]]!!
        score += moveVal[yourMove]!!
        score += vsPoints[moves[0]]!![yourMove]!!
    }
    print(score)
}