package day4

fun IntRange.isSubrangeOf(other: IntRange): Boolean {
    return this.first >= other.first && this.last <= other.last
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return this.intersect(other).isNotEmpty()
}

fun part1() {
    var count = 0
    common.inputFor(4).forEachLine {
        val (a1, b1, a2, b2) = it.split(",", "-")
        val r1 = a1.toInt()..b1.toInt()
        val r2 = a2.toInt()..b2.toInt()
        println("$r1 $r2 ${r1.isSubrangeOf(r2)} ${r2.isSubrangeOf(r1)}")
        if (r1.isSubrangeOf(r2) || r2.isSubrangeOf(r1)) {
            count += 1
        }
    }
    println(count)
}

fun part2() {
    var count = 0
    common.inputFor(4).forEachLine {
        val (a1, b1, a2, b2) = it.split(",", "-")
        val r1 = a1.toInt()..b1.toInt()
        val r2 = a2.toInt()..b2.toInt()
        println("$r1 $r2 ${r1.overlaps(r2)}")
        if (r1.overlaps(r2)) {
            count += 1
        }
    }
    println(count)
}

fun main() {
    part2()
}