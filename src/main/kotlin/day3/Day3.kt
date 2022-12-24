package day3

import common.inputFor

fun Char.toPriority(): Int {
    if (this.code >= 'a'.code) {
        return this.code - 'a'.code + 1
    }
    return this.code - 'A'.code + 27
}

fun part1() {
    var sum = 0
    inputFor(3).forEachLine {
        val k1 = it.substring(0, it.length / 2)
        val k2 = it.substring(it.length / 2)
        val both = k1.toSet().intersect(k2.toSet())
        assert(both.size == 1)
        val pri = both.first().toPriority()
        println("${both.first()} ${pri}")
        sum += pri
    }
    print(sum)
}

fun part2() {
    var sum = 0
    val lines = inputFor(3).readLines()
    for (n in lines.indices step 3) {
        val badge = lines[n + 0].toSet()
            .intersect(lines[n + 1].toSet())
            .intersect(lines[n + 2].toSet())
        assert(badge.size == 1)
        println("${badge.first()} ${badge.first().toPriority()}")
        sum += badge.first().toPriority()
    }
    print(sum)
}

fun main() {
    part2()
}