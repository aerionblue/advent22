package day8

import common.inputFor

typealias Forest = List<List<Int>>

fun parseForest(lines: List<String>): Forest {
    return lines.map { line ->
        line.toList().map {
            it.toString().toInt()
        }
    }
}

fun Forest.lookUp(r: Int, c: Int): List<Int> {
    return (r - 1 downTo 0).map { this[it][c] }
}

fun Forest.lookDown(r: Int, c: Int): List<Int> {
    return (r + 1 until this.size).map { this[it][c] }
}

fun Forest.lookLeft(r: Int, c: Int): List<Int> {
    return this[r].slice(c - 1 downTo 0)
}

fun Forest.lookRight(r: Int, c: Int): List<Int> {
    return this[r].slice(c + 1 until this[r].size)
}

fun isVisible(f: Forest, r: Int, c: Int): Boolean {
    val height = f[r][c]
    return f.lookUp(r, c).all { it < height } ||
            f.lookDown(r, c).all { it < height } ||
            f.lookLeft(r, c).all { it < height } ||
            f.lookRight(r, c).all { it < height }
}

fun part1() {
    val f = parseForest(inputFor(8).readLines())
    val rows = f.size
    val cols = f[0].size
    val total = (0 until rows).map { r ->
        (0 until cols).map { c ->
            if (isVisible(f, r, c)) 1 else 0
        }
    }.flatten().sum()
    print(total)
}

private fun List<Int>.countLeadingMatches(pred: (Int) -> Boolean): Int {
    var n = 0
    this.forEach {
        n++
        if (!pred(it)) {
            return n
        }
    }
    return n
}

fun scenicScore(f: Forest, r: Int, c: Int): Int {
    val height = f[r][c]
    return sequenceOf(f.lookUp(r, c), f.lookDown(r, c), f.lookLeft(r, c), f.lookRight(r, c))
        .map { trees -> trees.countLeadingMatches { it < height } }
        .fold(1) { acc, el -> acc * el }
}

fun part2() {
    val f = parseForest(inputFor(8).readLines())
    val rows = f.size
    val cols = f[0].size
    println((0 until rows).map { r ->
        (0 until cols).map { c ->
            scenicScore(f, r, c)
        }
    }.flatten().max())
}

fun main() {
    part2()
}