package day11

fun debug(s: String) {
    // println(s)
}

/** A function of old worry level -> new worry level. */
typealias Operation = (Int) -> Int

data class Thrown(val item: Item, val target: Int)

fun itemList(monkeyMods: List<Int>, vararg worries: Int): List<Item> {
    return worries.map { Item(it, monkeyMods) }
}

class Monkey constructor(
    private val id: Int,
    initialItems: List<Item>,
    private val op: Operation,
    private val targets: Pair<Int, Int>,
) {
    val items = initialItems.toMutableList()
    var inspections: Long = 0

    fun inspectAndThrowAll(): Sequence<Thrown> {
        return sequence {
            while (items.isNotEmpty()) {
                yield(inspectAndThrowNext())
            }
        }
    }

    private fun inspectAndThrowNext(): Thrown {
        inspections++
        val item = items.removeFirst()
        debug("Monkey inspects item w/ worry ${item.worryForMonkey(id)}")
        item.applyWorry(op)
        debug("Item worry level -> ${item.worryForMonkey(id)}")
        return Thrown(item, decideTarget(item.worryForMonkey(id))).also {
            debug("Thrown to monkey ${it.target}")
        }
    }

    private fun decideTarget(worry: Int): Int {
        return if (worry == 0) targets.first else targets.second
    }

    fun catch(item: Item) {
        items.add(item)
    }
}

class Item constructor(initWorry: Int, val monkeyMods: List<Int>) {
    private var monkeyWorries = monkeyMods.map { mod -> initWorry % mod }

    fun worryForMonkey(id: Int) = monkeyWorries[id]

    fun applyWorry(op: Operation) {
        monkeyWorries = monkeyWorries.zip(monkeyMods)
            .map { (w, mod) -> op(w) % mod }
    }
}

val sampleMonkeyMods = listOf(23, 19, 13, 17)
val sampleMonkeys = listOf(
    Monkey(
        0,
        itemList(sampleMonkeyMods, 79, 98),
        { old -> old * 19 },
        2 to 3,
    ),
    Monkey(
        1,
        itemList(sampleMonkeyMods, 54, 65, 75, 74),
        { old -> old + 6 },
        2 to 0,
    ),
    Monkey(
        2,
        itemList(sampleMonkeyMods, 79, 60, 97),
        { old -> old * old },
        1 to 3,
    ),
    Monkey(
        3,
        itemList(sampleMonkeyMods, 74),
        { old -> old + 3 },
        0 to 1,
    ),
)

val realMonkeyMods = listOf(7, 11, 13, 3, 17, 2, 5, 19)
val realMonkeys = listOf(
    Monkey(
        0,
        itemList(realMonkeyMods, 63, 57),
        { old -> old * 11 },
        6 to 2,
    ),
    Monkey(
        1,
        itemList(realMonkeyMods, 82, 66, 87, 78, 77, 92, 83),
        { old -> old + 1 },
        5 to 0,
    ),
    Monkey(
        2,
        itemList(realMonkeyMods, 97, 53, 53, 85, 58, 54),
        { old -> old * 7 },
        4 to 3,
    ),
    Monkey(
        3,
        itemList(realMonkeyMods, 50),
        { old -> old + 3 },
        1 to 7,
    ),
    Monkey(
        4,
        itemList(realMonkeyMods, 64, 69, 52, 65, 73),
        { old -> old + 6 },
        3 to 7,
    ),
    Monkey(
        5,
        itemList(realMonkeyMods, 57, 91, 65),
        { old -> old + 5 },
        0 to 6,
    ),
    Monkey(
        6,

        itemList(realMonkeyMods, 67, 91, 84, 78, 60, 69, 99, 83),
        { old -> old * old },
        2 to 4,
    ),
    Monkey(
        7,
        itemList(realMonkeyMods, 58, 78, 69, 65),
        { old -> old + 7 },
        5 to 1,
    ),
)

// part1 doesn't work now that I deleted the worry /= 3 code
fun part1() {
    val monkeys = sampleMonkeys
    repeat(20) { round ->
        monkeys.forEach {
            it.inspectAndThrowAll().forEach { (item, target) -> monkeys[target].catch(item) }
        }
        println("")
        println("Round ${round + 1}")
        monkeys.printState()
    }
    val most = monkeys.sortedBy { it.inspections }.reversed()
    println(most[0].inspections * most[1].inspections)
}

fun List<Monkey>.printState() {
    this.forEachIndexed { idx, m ->
        println("Monkey $idx: " + m.items.map { it.worryForMonkey(idx) }.joinToString(", "))
    }
}

fun List<Monkey>.printCounts() {
    forEachIndexed { idx, m ->
        println("Monkey $idx inspected items ${m.inspections} times.")
    }
}

fun part2() {
    val monkeys = realMonkeys
    repeat(10000) { round ->
        monkeys.forEach {
            it.inspectAndThrowAll().forEach { (item, target) -> monkeys[target].catch(item) }
        }
        if (round == 0 || round + 1 % 1000 == 0) {
            println("== After round ${round + 1} ==")
            monkeys.printCounts()
            println()
        }
    }
    monkeys.printCounts()
    val most = monkeys.sortedBy { it.inspections }.reversed()
    println(most[0].inspections * most[1].inspections)
}

fun main() {
    // part1()
    part2()
}