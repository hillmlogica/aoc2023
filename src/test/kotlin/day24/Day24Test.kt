package day24

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day24Test {
    val exampleInput = """19, 13, 30 @ -2,  1, -2
18, 19, 22 @ -1, -1, -2
20, 25, 34 @ -2, -2, -4
12, 31, 28 @ -1, -2, -1
20, 19, 15 @  1, -5, -3"""

    @Test
    fun `check part 1 example`() {
        Assertions.assertThat(answer(exampleInput, Box(Coord(7, 7), Coord(27, 27)))).isEqualTo(2)
    }
    @Test
    fun `check part 2 example`() {
        Assertions.assertThat(answer2(exampleInput)).isEqualTo(0)
    }

    @Test
    fun `check where two hailstones meet example 1_1`() {
        val a = Hailstone(19, 13, 30, -2, 1, -2)
        val b = Hailstone(18, 19, 22, -1, -1, -2)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(CoordD(14.333333333333334, 15.333333333333334))
    }
    @Test
    fun `check where two hailstones meet example 1_2`() {
        val a = Hailstone(19, 13, 30, -2, 1, -2)
        val b = Hailstone(20, 25, 34, -2, -2, -4)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(CoordD(11.666666666666666, 16.666666666666668))
    }
    @Test
    fun `check where two hailstones meet example 1_3`() {
        val a = Hailstone(19, 13, 30, -2, 1, -2)
        val b = Hailstone(12, 31, 28, -1, -2, -1)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(CoordD(6.2, 19.4))
    }
    @Test
    fun `check where two hailstones meet example 1_4`() {
        val a = Hailstone(19, 13, 30, -2, 1, -2)
        val b = Hailstone(20, 19, 15, 1, -5, -3)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }
    @Test
    fun `check where two hailstones meet example 1_5`() {
        val a = Hailstone(18, 19, 22, -1, -1, -2)
        val b = Hailstone(20, 25, 34, -2, -2, -4)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }
    @Test
    fun `check where two hailstones meet example 1_6`() {
        val a = Hailstone(18, 19, 22, -1, -1, -2)
        val b = Hailstone(12, 31, 28, -1, -2, -1)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(CoordD(-6.0, -5.0))
    }
    @Test
    fun `check where two hailstones meet example 1_7`() {
        val a = Hailstone(18, 19, 22, -1, -1, -2)
        val b = Hailstone(20, 19, 15, 1, -5, -3)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }
    @Test
    fun `check where two hailstones meet example 1_8`() {
        val a = Hailstone(20, 25, 34, -2, -2, -4)
        val b = Hailstone(12, 31, 28, -1, -2, -1)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(CoordD(-2.0, 3.0))
    }
    @Test
    fun `check where two hailstones meet example 1_9`() {
        val a = Hailstone(20, 25, 34, -2, -2, -4)
        val b = Hailstone(20, 19, 15, 1, -5, -3)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }
    @Test
    fun `check where two hailstones meet example 1_10`() {
        val a = Hailstone(12, 31, 28, -1, -2, -1)
        val b = Hailstone(20, 19, 15, 1, -5, -3)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }

}