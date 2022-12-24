package day12

import common.inputFor

typealias Point = Pair<Int, Int>
typealias Map2D = List<List<Int>>

fun Map2D.width() = this[0].size
fun Map2D.height() = this.size
fun Map2D.at(p: Point) = this[p.first][p.second]

data class ElevationMap(val elevations: Map2D, val start: Point, val end: Point)

fun elev(c: Char): Int {
    return when (c) {
        'S' -> 1
        'E' -> 26
        else -> c.code - 'a'.code + 1
    }
}

fun parseMap(lines: List<String>): ElevationMap {
    val elevs = mutableListOf<List<Int>>()
    var start: Point? = null
    var end: Point? = null
    lines.forEachIndexed { rowNum, row ->
        elevs.add(row.map { elev(it) })
        row.indexOf('S').let {
            if (it >= 0) {
                start = rowNum to it
            }
        }
        row.indexOf('E').let {
            if (it >= 0) {
                end = rowNum to it
            }
        }
    }
    return ElevationMap(
        elevs,
        start ?: throw IllegalArgumentException("missing 'S'"),
        end ?: throw IllegalArgumentException("missing 'E'"),
    )
}

fun Point.unvisitedNeighbors(distances: Map2D): List<Point> {
    return listOf(
        this.first to this.second - 1,
        this.first to this.second + 1,
        this.first - 1 to this.second,
        this.first + 1 to this.second,
    )
        .filter { (r, c) -> r >= 0 && r < distances.height() && c >= 0 && c < distances.width() }
        .filter { distances.at(it) < 0 }
}

fun List<Point>.println() {
    println(this.map { (r, c) -> "($r, $c)" }.joinToString(" "))
}

fun part1() {
    val m = parseMap(inputFor(12).readLines())
    // Flood fill starting from the end (because part 2 will be easier)
    val distances = m.elevations.mapIndexed { r, row ->
        row.indices.map { c ->
            if (m.end == (r to c)) 0 else Int.MIN_VALUE
        }.toMutableList()
    }.toMutableList()
    val visitQueue = mutableListOf(m.end)
    while (visitQueue.isNotEmpty()) {
        // visitQueue.println()
        val p = visitQueue.removeFirst()
        val distance = distances.at(p)
        val nexts = p.unvisitedNeighbors(distances).filter {
            val here = m.elevations.at(p)
            val there = m.elevations.at(it)
            there >= here - 1
        }
        // nexts.println()
        nexts.forEach { (r, c) ->
            distances[r][c] = distance + 1
        }
        visitQueue.addAll(nexts)
    }
    val finalDist = distances.at(m.start)
    println(finalDist)

    // Part 2: Find the 'a'-elevation spot that we reached first
    var bestStartDist = finalDist
    for (r in 0 until m.elevations.height()) {
        for (c in 0 until m.elevations.width()) {
            if (m.elevations.at(r to c) == 1) {
                val d = distances.at(r to c)
                if (d in 0..bestStartDist) {
                    bestStartDist = d
                }
            }
        }
    }
    println(bestStartDist)
}

fun main() {
    part1()
}