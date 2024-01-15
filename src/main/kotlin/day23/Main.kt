package day23

import java.io.File
import kotlin.math.max

fun main() {
    val puzzleInput = File("day23/puzzleInput.txt").readText()
    println("answer1: " + answer(puzzleInput))
    println("answer2: " + answer2(puzzleInput))

}

fun answer2(puzzleInput: String): Int {
    return 0
}

fun answer(puzzleInput: String): Int {
    val graph = parsePuzzleIntoGraph(puzzleInput)
    return graph.findLongestPath()
}

private fun parsePuzzleIntoGraph(puzzleInput: String): Graph {
    val grid = Grid(puzzleInput.lines())
    val startingNode = makeNode((0 until grid.width()).map { Coord(it, 0) }.first { grid.cellAt(it) == '.' })
    val endingNode =
        makeNode((0 until grid.width()).map { Coord(it, grid.height() - 1) }.first { grid.cellAt(it) == '.' })
    val queue = mutableListOf(Pair(startingNode, Direction.South))
    val graph = Graph(startingNode, endingNode)
    while (queue.isNotEmpty()) {
        val start = queue.removeFirst()
        var paths = grid.nextFor(listOf(Pair(start.first.coord, start.second)))
        while (paths.size == 1 && !graph.nodeCoords().contains(paths[0].last().second.applyTo(paths[0].last().first))) {
            paths = grid.nextFor(paths[0])
        }
        if (paths.size > 1) {
            val node = makeNode(paths.first().last().first)
            graph.addNode(node)
            graph.addPath(Path(start.first, node, paths[0].size - 1))
            queue.addAll(paths.map { Pair(node, it.last().second) })
        } else {
            val endNode = graph.nodeFor(paths[0].last().second.applyTo(paths[0].last().first))
            graph.addPath(Path(start.first, endNode, paths[0].size))
        }
    }
    return graph
}

class Graph(val start: Node, val end: Node) {
    val nodes = mutableListOf(start, end)
    val paths = mutableListOf<Path>()

    fun addNode(node: Node) {
        nodes.add(node)
    }

    fun addPath(path: Path) {
        paths.add(path)
    }

    fun nodeCoords(): List<Coord> = nodes.map { it.coord }
    fun nodeFor(coord: Coord): Node {
        return nodes.find { it.coord.equals(coord) }!!
    }

    fun findLongestPath(): Int {
        val nodeDistances = nodes.map { it to Int.MIN_VALUE }.toMap().toMutableMap()
        nodeDistances[start] = 0
        val queue = mutableListOf<Node>()
        val visited = mutableListOf<Node>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            visited.add(node)
            val pathsFromThisNode = paths.filter { it.from == node }
            pathsFromThisNode.forEach {
                nodeDistances[it.to] = max(nodeDistances[it.to]!!, nodeDistances[it.from]!! + it.distance)
                if (!visited.contains(it.to)) {
                    queue.add(it.to)
                }
            }
        }
        return nodeDistances[end]!!
    }
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