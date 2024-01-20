package day24

import java.io.File

fun main() {
    val puzzleInput = File("day24/puzzleInput.txt").readText()
    println("answer1: " + answer(puzzleInput, Box(Coord(7, 7), Coord(27, 27))))
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
        Hailstone(px.toLong(), py.toLong(), pz.toLong(), vx.toLong(), vy.toLong(), vz.toLong())
    }
    hailstones.forEach {
        println(it)
    }
    return 0
}

fun crossingPoint(a: Hailstone, b: Hailstone): CoordD? {
    val intersectionPoint = intersectionPoint(a.firstPoint(), a.secondPoint(), b.firstPoint(), b.secondPoint())
    return if (intersectionPoint== null || ((intersectionPoint.x - a.firstPoint().x)/a.vx > 0) && intersectionPoint.x - b.firstPoint().x/b.vx > 0) intersectionPoint else null
}


fun intersectionPoint(a: Coord, b: Coord, c: Coord, d: Coord): CoordD? {
    // a1x + b1y = c1
    val a1 = b.y - a.y
    val b1 = a.x - b.x
    val c1 = a1 * (a.x) + b1 * (a.y)

    // a2x + b2y = c2
    val a2 = d.y - c.y
    val b2 = c.x - d.x
    val c2 = a2 * (c.x) + b2 * (c.y)

    // determinant
    val det = (a1 * b2 - a2 * b1).toDouble()

    if (det == 0.0) return null

    // intersect point (x, y)
    val x = ((b2 * c1) - (b1 * c2)) / det
    val y = ((a1 * c2) - (a2 * c1)) / det
    return CoordD(x, y)
}
data class Hailstone(val px: Long, val py: Long, val pz: Long, val vx: Long, val vy: Long, val vz: Long) {
    fun firstPoint(): Coord {
        return Coord(px, py)
    }

    fun secondPoint(): Coord {
        return Coord(px+vx, py+vy)
    }
}

data class Coord(val x: Long, val y: Long)

data class Box(val left: Coord, val right: Coord)

data class CoordD(val x: Double, val y: Double)