package day6

fun go(window: Int) {
    val s = common.inputFor(6).readText().trim()
    for (index in window - 1 until s.length) {
        val packet = s.slice(index - (window - 1)..index)
        if (packet.toSet().size == window) {
            println(packet)
            println(index + 1)
            return
        }
    }
}

fun main() {
    go(14)
}