package day7

import common.inputFor

data class Dir(val parent: Dir?) {
    private val dirs = mutableMapOf<String, Dir>()
    private val files = mutableMapOf<String, File>()

    fun createDir(name: String): Dir {
        return Dir(this).also { dirs[name] = it }
    }

    fun createFile(name: String, size: Int): File {
        return File(this, size).also { files[name] = it }
    }

    /** Return the named subdirectory. If it doesn't exist, create it. */
    fun findOrCreate(name: String) = dirs[name] ?: createDir(name)

    fun size(): Int {
        return dirs.values.sumOf { it.size() } + files.values.sumOf { it.size }
    }

    fun <V> dirMap(visit: (Dir) -> V): Sequence<V> {
        return sequence {
            yield(visit(this@Dir))
            dirs.values.forEach { yieldAll(it.dirMap(visit)) }
        }
    }

    fun <V> fileMap(visit: (File) -> V): Sequence<V> {
        return sequence {
            files.values.forEach { yield(visit(it)) }
            dirs.values.forEach { yieldAll(it.fileMap(visit)) }
        }
    }

    fun ls(indent: Int = 0) {
        files.forEach { (name, f) ->
            println("  ".repeat(indent) + "${f.size} $name")
        }
        dirs.forEach { (name, d) ->
            println("  ".repeat(indent) + "dir $name")
            d.ls(indent + 1)
        }
    }
}

data class File(val parent: Dir, val size: Int)

fun buildFs(): Dir {
    val root = Dir(null)
    var pwd = root
    inputFor(7).forEachLine {
        when {
            it.startsWith("$ cd ") -> {
                val dest = it.split(" ").last()
                pwd = when (dest) {
                    "/" -> root
                    ".." -> pwd.parent ?: throw IllegalArgumentException("`cd ..` at root")
                    else -> pwd.findOrCreate(dest)
                }
            }

            it.startsWith("$ ls") -> {}

            it.startsWith("dir ") -> {
                val dirName = it.split(" ").last()
                pwd.createDir(dirName)
            }

            else -> {
                val (sizeStr, name) = it.split(" ")
                pwd.createFile(name, sizeStr.toInt())
            }
        }
    }
    return root
}

fun part1() {
    val root = buildFs()
    root.ls()
    println(root.dirMap { it.size() }.filter { it <= 100000 }.sum())
}

private const val DISK_SIZE = 70000000
private const val TARGET_FREE_SPACE = 30000000
private const val TARGET_USED_SPACE = DISK_SIZE - TARGET_FREE_SPACE

fun part2() {
    val root = buildFs()
    val used = root.fileMap { it.size }.sum()
    val sizeToDelete = used - TARGET_USED_SPACE
    println(root.dirMap { it.size() }.filter { it >= sizeToDelete }.min())
}

fun main() {
    part2()
}
