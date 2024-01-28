package day25

import java.io.File
import kotlin.math.abs
import kotlin.random.Random

fun main() {
    val puzzleInput = File("day25/puzzleInput.txt").readText()
    println("answer1: " + answer(puzzleInput))
    println("answer2: " + answer2(puzzleInput))
}

fun answer(puzzleInput: String): Int {
    val edges = puzzleInput.lines().flatMap {
        val nodes = it.split(" ")
        val lhs = nodes[0].dropLast(1)
        nodes.subList(1, nodes.size).map { Edge(lhs, it) }
    }
    return best3(edges)
}

fun answer2(puzzleInput: String): Int {
    return 0
}

fun best3(edges: List<Edge>): Int {
    var counter = 0
    val histogram = mutableMapOf<Edge, Int>()
    val graph = makeGraphFrom(edges)
    while(true) {
        val minCut = graph.kargerMinCut()
        if (minCut.edges.size == 3) {
            return minCut.vertices[0].size * minCut.vertices[1].size
        }
        counter++
        minCut.edges.forEach {
            histogram.compute(it) { t, u ->
                if (u == null) {
                    1
                } else {
                    u + 1
                }
            }
        }
        if (counter % 10 == 0) {
            println("--- After iteration $counter ---")
            histogram.entries.sortedByDescending { it.value }.forEach {
                println("${it.key.node1} - ${it.key.node2}: " + it.value)
            }
        }
    }
}

private fun makeGraphFrom(edges: List<Edge>): Graph {
    val vertices = edges.flatMap { listOf(it.node1, it.node2) }.toSet().map { listOf(it) }.toList()
    var graph = Graph(edges, vertices)
    return graph
}

class Graph(val edges: List<Edge>, val vertices: List<List<String>>) {
    fun nodeCount(): Int {
        return vertices.size
    }

    fun kargerMinCut() : Graph {
        var graph = this
        while(graph.nodeCount() > 2) {
            val edgeIndex = abs(Random.nextInt()) % graph.edges.size
            val randomEdge = graph.edges[edgeIndex]
            graph = graph.contract(randomEdge)
        }
        return graph
    }

    fun contract(randomEdge: Edge): Graph {
        val nodeBasket1 = vertices.find { it.contains(randomEdge.node1) }!!
        val nodeBasket2 = vertices.find { it.contains(randomEdge.node2) }!!
        val mergedBasket = nodeBasket1.plus(nodeBasket2)
        val newVertices = vertices.toMutableList()
        newVertices.remove(nodeBasket1)
        newVertices.remove(nodeBasket2)
        newVertices.add(mergedBasket)
        val newEdges = edges.filterNot{ mergedBasket.contains(it.node1) && mergedBasket.contains(it.node2) }
        return Graph(newEdges, newVertices)
    }

}

data class Edge(val node1: String, val node2: String)