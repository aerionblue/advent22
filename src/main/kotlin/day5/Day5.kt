package day5

import kotlin.math.ceil

typealias Stack = MutableList<Char>

fun Stack.push(crate: Char) = this.add(crate)
fun Stack.pop() = this.removeLast()

typealias Stacks = List<Stack>

fun Stacks.doMove(m: Move) {
    (1..m.n).forEach {
        this[m.to].push(this[m.from].pop())
    }
}

fun Stacks.doMovePart2(m: Move) {
    val moved = (1..m.n).map { this[m.from].pop() }.reversed()
    this[m.to].addAll(this[m.to].size, moved)
}

fun Stacks.print() {
    forEachIndexed { i, stack ->
        println("$i  ${stack.joinToString("")}")
    }
    println()
}

data class Move(val n: Int, val from: Int, val to: Int)

fun parseMove(s: String): Move {
    val tokens = s.split(" ")
    assert(tokens[0] == "move")
    assert(tokens[2] == "from")
    assert(tokens[4] == "to")
    return Move(tokens[1].toInt(), tokens[3].toInt() - 1, tokens[5].toInt() - 1)
}

fun String.crateAt(stackNum: Int): Char? {
    val charNum = 1 + stackNum * 4
    if (charNum >= this.length)
        return null
    val crate = this[charNum]
    return if (crate.isWhitespace()) null else crate
}

fun parseStacks(lines: List<String>): List<Stack> {
    val numStacks = ceil(lines.last().length / 4.0).toInt()
    val stacks = mutableListOf<Stack>()
    for (i in 0 until numStacks) {
        stacks.add(mutableListOf())
    }

    for (n in lines.size - 2 downTo 0) {
        (0 until numStacks)
            .forEach { stackNum ->
                lines[n].crateAt(stackNum)?.let {
                    stacks[stackNum].push(it)
                }
            }
    }
    return stacks
}

fun part1() {
    val lines = common.inputFor(5).readLines()
    val divider = lines.indexOf("")
    val stacks = parseStacks(lines.slice(0 until divider))
    val moves = lines.subList(divider + 1, lines.size).map { parseMove(it) }
    stacks.print()
    moves.forEach {
        stacks.doMove(it)
        stacks.print()
    }
    println(stacks.map { it.last() }.joinToString(""))
}

fun part2() {
    val lines = common.inputFor(5).readLines()
    val divider = lines.indexOf("")
    val stacks = parseStacks(lines.slice(0 until divider))
    val moves = lines.subList(divider + 1, lines.size).map { parseMove(it) }
    stacks.print()
    moves.forEach {
        stacks.doMovePart2(it)
        stacks.print()
    }
    println(stacks.map { it.last() }.joinToString(""))
}

fun main() {
    part2()
}