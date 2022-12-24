package day10

import common.inputFor
import kotlin.math.abs

fun part1() {
    val xs = mutableListOf(1)
    var x = 1
    inputFor(10).forEachLine { line ->
        when {
            line == "noop" -> xs.add(x)
            line.startsWith("addx") -> {
                val addend = line.split(" ").last().toInt()
                xs.add(x)
                x += addend
                xs.add(x)
            }
        }
    }
    println((20..xs.size step 40).map { it * xs[it - 1] }.sum())
}

fun part2() {
    val xs = mutableListOf(1)
    var x = 1
    inputFor(10).forEachLine { line ->
        when {
            line == "noop" -> xs.add(x)
            line.startsWith("addx") -> {
                val addend = line.split(" ").last().toInt()
                xs.add(x)
                x += addend
                xs.add(x)
            }
        }
    }
    for (row in 0 until 6) {
        println((0 until 40).map { col ->
            if (abs(xs[40 * row + col] - col) < 2) '#' else '.'
        }.joinToString(""))
    }
}

fun main() {
    part2()
}