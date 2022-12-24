package common

import java.io.File

fun inputFor(n: Int, sample: Boolean = false): File {
    return if (sample) {
        File("src/main/kotlin/day$n/sample$n.txt")
    } else {
        File("src/main/kotlin/day$n/in$n.txt")
    }
}
