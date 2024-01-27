package day24

import java.io.File
import java.math.BigDecimal
import java.math.MathContext

fun main() {
    val puzzleInput = File("day24/puzzleInput.txt").readText()
    println("answer1: " + answer(puzzleInput, Box(Coord(BigDecimal(200000000000000), BigDecimal(200000000000000)), Coord(BigDecimal(400000000000000), BigDecimal(400000000000000)))))
    println("answer2: " + answer2(puzzleInput))
}

fun answer2(puzzleInput: String): Int {
    return 0
}

fun answer(puzzleInput: String, box: Box): Int {
    val hailstones = puzzleInput.lines().map {
//        "19, 13, 30 @ -2,  1, -2"
        val match = "(\\d+), +(\\d+), +(\\d+) +@ +(-?\\d+), +(-?\\d+), +(-?\\d+)".toRegex().matchEntire(it)
        val (px, py, pz, vx, vy, vz) = match!!.destructured
        Hailstone(BigDecimal(px), BigDecimal(py), BigDecimal(pz), BigDecimal(vx), BigDecimal(vy), BigDecimal(vz))
    }
//    val minX = hailstones.map { it.px }.min()
//    val maxX = hailstones.map { it.px }.max()
//    println(minX)
//    println(maxX)
//    return 0
    val permutations = permutations(hailstones)
    val nonNullCrossingPoints = permutations.map { crossingPoint(it.first, it.second) }.filterNotNull()
    return nonNullCrossingPoints.filter { box.contains(it) }.size
}


tailrec fun permutations(hailstones: List<Hailstone>): List<Pair<Hailstone, Hailstone>> {
    if (hailstones.size < 2) return emptyList()
    val thisOne = hailstones[0]
    val rest = hailstones.drop(1)
    return rest.map { Pair(thisOne, it) } + permutations(rest)
}

fun crossingPoint(a: Hailstone, b: Hailstone): Coord? {
    val intersectionPoint = intersectionPoint(a.firstPoint(), a.secondPoint(), b.firstPoint(), b.secondPoint())
    if (intersectionPoint == null) return null
    val nanosForA = (intersectionPoint.x - a.firstPoint().x) / a.vx
    val nanosForB = (intersectionPoint.x - b.firstPoint().x) / b.vx
    return if (nanosForA.compareTo(BigDecimal.ZERO) > 0 && nanosForB.compareTo(BigDecimal.ZERO) > 0) intersectionPoint else null
}

fun intersectionPoint(a: Coord, b: Coord, c: Coord, d: Coord): Coord? {
    // a1x + b1y = c1
    val a1 = b.y - a.y
    val b1 = a.x - b.x
    val c1 = a1 * (a.x) + b1 * (a.y)

    // a2x + b2y = c2
    val a2 = d.y - c.y
    val b2 = c.x - d.x
    val c2 = a2 * (c.x) + b2 * (c.y)

    // determinant
    val det = (a1 * b2 - a2 * b1)

    if (det.compareTo(BigDecimal.ZERO) == 0) return null

    // intersect point (x, y)
    val x = ((b2 * c1) - (b1 * c2)).divide(det, MathContext.DECIMAL128)
    val y = ((a1 * c2) - (a2 * c1)).divide(det, MathContext.DECIMAL128)
    return Coord(x, y)
}

data class Hailstone(val px: BigDecimal, val py: BigDecimal, val pz: BigDecimal, val vx: BigDecimal, val vy: BigDecimal, val vz: BigDecimal) {
    fun firstPoint(): Coord {
        return Coord(px, py)
    }

    fun secondPoint(): Coord {
        return Coord(px + vx, py + vy)
    }

    companion object {
        fun hailstone(px: Int, py: Int, pz: Int, vx: Int, vy: Int, vz: Int): Hailstone {
            return Hailstone(BigDecimal(px), BigDecimal(py), BigDecimal(pz), BigDecimal(vx), BigDecimal(vy), BigDecimal(vz))
        }
    }
}

data class Coord(val x: BigDecimal, val y: BigDecimal) {
    companion object {
        fun coord(x: Int, y: Int): Coord {
            return Coord(BigDecimal(x), BigDecimal(y))
        }
    }
}

data class Box(val left: Coord, val right: Coord) {
    fun contains(coord: Coord): Boolean {
        return left.x <= coord.x && right.x >= coord.x
                && left.y <= coord.y && right.y >= coord.y
    }
}