package day23

import java.io.File

fun main() {
    val puzzleInput = File("day23/puzzleInput.txt").readText()
    println("answer1: " + answer(puzzleInput))
    println("answer2: " + answer2(puzzleInput))

}

fun answer2(puzzleInput: String): Int {
    return 0
}

fun answer(puzzleInput: String): Int {
    val grid = Grid(puzzleInput.lines())
    val startingNode = makeNode((0 until grid.width()).map { Coord(it, 0) }.first { grid.cellAt(it) == '.' })
    val endingNode = makeNode((0 until grid.width()).map { Coord(it, grid.height() - 1) }.first { grid.cellAt(it) == '.' })
    val queue = mutableListOf(Pair(startingNode, Direction.South))
    val discoveredNodes = mutableListOf<Node>(startingNode, endingNode)
    val discoveredPaths = mutableListOf<Path>()
    while (queue.isNotEmpty()) {
        val start = queue.removeFirst()
        var paths = grid.nextFor(listOf(Pair(start.first.coord, start.second)))
        while (paths.size == 1 && !discoveredNodes.map { it.coord }.contains(paths[0].last().second.applyTo(paths[0].last().first))) {
            paths = grid.nextFor(paths[0])
        }
        if (paths.size > 1) {
            val node = makeNode(paths.first().last().first)
            discoveredNodes.add(node)
            discoveredPaths.add(Path(startingNode, node, paths[0].size - 1))
            queue.addAll(paths.map { Pair(node, it.last().second) })
        } else {
            val endNode = discoveredNodes.find { it.coord.equals(paths[0].last().second.applyTo(paths[0].last().first)) }!!
            discoveredPaths.add(Path(startingNode, endNode, paths[0].size))
        }
    }

    return 0
}

data class Path(val from: Node, val to: Node, val distance: Int)

var nodeCount = 0

fun makeNode(coord: Coord) : Node {
    nodeCount++
    return Node("N-$nodeCount", coord)
}
data class Node(val name: String, val coord: Coord)

data class Coord(val x: Int, val y: Int) {
}

data class Grid(val lines: List<String>) {
    fun cellAt(c: Coord): Char {
        return lines[c.y][c.x]
    }

    fun width() = lines.first().length

    fun height() = lines.size

    fun contains(c: Coord) = c.x >= 0 && c.x < width() && c.y >= 0 && c.y < height()
    fun nextFor(path: List<Pair<Coord, Direction>>): List<List<Pair<Coord, Direction>>> {
        val nextC = path.last().second.applyTo(path.last().first)
        val all = directionsFor(cellAt(nextC))
        return all.map { Pair(it, it.applyTo(nextC)) }
            .filter { contains(it.second) }
            .filter { !path.map { it.first }.contains(it.second) }
            .filter { cellAt(it.second) == '.' ||
                    (cellAt(it.second) == '^' && it.first != Direction.South) ||
                    (cellAt(it.second) == '>' && it.first != Direction.West) ||
                    (cellAt(it.second) == 'v' && it.first != Direction.North) ||
                    (cellAt(it.second) == '<' && it.first != Direction.East)
            }
            .map { path + Pair(nextC, it.first) }

    }

    fun directionsFor(cell: Char): List<Direction> {
        return when (cell) {
            '.' -> Direction.entries
            '>' -> listOf(Direction.East)
            'v' -> listOf(Direction.South)
            '<' -> listOf(Direction.West)
            '^' -> listOf(Direction.North)
            else -> throw RuntimeException("Don't know how to do directions from $cell")
        }
    }
}
//^, >, v, and <

enum class Direction {
    North {
        override fun applyTo(c: Coord) = c.copy(y = c.y - 1)
    },
    South {
        override fun applyTo(c: Coord) = c.copy(y = c.y + 1)
    },
    East {
        override fun applyTo(c: Coord) = c.copy(x = c.x + 1)
    },
    West {
        override fun applyTo(c: Coord) = c.copy(x = c.x - 1)
    };

    abstract fun applyTo(c: Coord): Coord
}