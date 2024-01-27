package day24

import day24.Coord.Companion.coord
import day24.Hailstone.Companion.hailstone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class Day24Test {
    val exampleInput = """19, 13, 30 @ -2,  1, -2
18, 19, 22 @ -1, -1, -2
20, 25, 34 @ -2, -2, -4
12, 31, 28 @ -1, -2, -1
20, 19, 15 @  1, -5, -3"""

    @Test
    fun `check part 1 example`() {
        Assertions.assertThat(answer(exampleInput, Box(coord(7, 7), coord(27, 27)))).isEqualTo(2)
    }
    @Test
    fun `check part 2 example`() {
        Assertions.assertThat(answer2(exampleInput)).isEqualTo(0)
    }

    @Test
    fun `check where two hailstones meet example 1_1`() {
        val a = hailstone(19, 13, 30, -2, 1, -2)
        val b = hailstone(18, 19, 22, -1, -1, -2)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(Coord(BigDecimal("14.33333333333333333333333333333333"), BigDecimal("15.33333333333333333333333333333333")))
    }
    @Test
    fun `check where two hailstones meet example 1_2`() {
        val a = hailstone(19, 13, 30, -2, 1, -2)
        val b = hailstone(20, 25, 34, -2, -2, -4)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(Coord(BigDecimal("11.66666666666666666666666666666667"), BigDecimal("16.66666666666666666666666666666667")))
    }
    @Test
    fun `check where two hailstones meet example 1_3`() {
        val a = hailstone(19, 13, 30, -2, 1, -2)
        val b = hailstone(12, 31, 28, -1, -2, -1)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(Coord(BigDecimal("6.2"), BigDecimal("19.4")))
    }
    @Test
    fun `check where two hailstones meet example 1_4`() {
        val a = hailstone(19, 13, 30, -2, 1, -2)
        val b = hailstone(20, 19, 15, 1, -5, -3)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }
    @Test
    fun `check where two hailstones meet example 1_5`() {
        val a = hailstone(18, 19, 22, -1, -1, -2)
        val b = hailstone(20, 25, 34, -2, -2, -4)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }
    @Test
    fun `check where two hailstones meet example 1_6`() {
        val a = hailstone(18, 19, 22, -1, -1, -2)
        val b = hailstone(12, 31, 28, -1, -2, -1)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(Coord(BigDecimal("-6"), BigDecimal("-5")))
    }
    @Test
    fun `check where two hailstones meet example 1_7`() {
        val a = hailstone(18, 19, 22, -1, -1, -2)
        val b = hailstone(20, 19, 15, 1, -5, -3)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }
    @Test
    fun `check where two hailstones meet example 1_8`() {
        val a = hailstone(20, 25, 34, -2, -2, -4)
        val b = hailstone(12, 31, 28, -1, -2, -1)
        Assertions.assertThat(crossingPoint(a, b)).isEqualTo(Coord(BigDecimal("-2"), BigDecimal("3")))
    }
    @Test
    fun `check where two hailstones meet example 1_9`() {
        val a = hailstone(20, 25, 34, -2, -2, -4)
        val b = hailstone(20, 19, 15, 1, -5, -3)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }
    @Test
    fun `check where two hailstones meet example 1_10`() {
        val a = hailstone(12, 31, 28, -1, -2, -1)
        val b = hailstone(20, 19, 15, 1, -5, -3)
        Assertions.assertThat(crossingPoint(a, b)).isNull()
    }

}