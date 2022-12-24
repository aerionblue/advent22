package day1

import common.inputFor

fun main(args: Array<String>) {
    var sum = 0
    var maxSum = 0
    val sums = mutableListOf<Int>()
    inputFor(1).forEachLine {
        if (it.isEmpty()) {
            sums.add(sum)
            sum = 0
        } else {
            sum += Integer.valueOf(it)
        }
    }
    sums.add(sum)
    val sorted = sums.sortedBy { -it }
    println(sorted[0])
    println(sorted[1])
    println(sorted[2])
    println(sorted[0] + sorted[1] + sorted[2])
}