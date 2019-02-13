package databinding.lint

import org.hamcrest.CoreMatchers.hasItem
import org.junit.Assert.assertThat
import org.junit.Test

class ExpressionParserTest {

    private val parser = ExpressionParser()
    private fun opTypes(expr: String): Set<OpType> {
        return parser.parse(expr)
    }

    @Test
    fun testCastOpType() {
        assertThat(opTypes("(A) a"), hasItem(CAST))
    }

    @Test
    fun testUnaryOpTypes() {
        assertThat(opTypes("+a"), hasItem(UNARY))
        assertThat(opTypes("-a"), hasItem(UNARY))
        assertThat(opTypes("!a"), hasItem(UNARY))
        assertThat(opTypes("~a"), hasItem(UNARY))
    }

    @Test
    fun testMathOpTypes() {
        assertThat(opTypes("a + b"), hasItem(MATH))
        assertThat(opTypes("a - b"), hasItem(MATH))
        assertThat(opTypes("a * b"), hasItem(MATH))
        assertThat(opTypes("a / b"), hasItem(MATH))
        assertThat(opTypes("a % b"), hasItem(MATH))
    }

    @Test
    fun testBitShiftOpTypes() {
        assertThat(opTypes("a << b"), hasItem(BIT_SHIFT))
        assertThat(opTypes("a >> b"), hasItem(BIT_SHIFT))
        assertThat(opTypes("a >>> b"), hasItem(BIT_SHIFT))
    }

    @Test
    fun testCompareOpTypes() {
        assertThat(opTypes("a < b"), hasItem(COMPARE))
        assertThat(opTypes("a <= b"), hasItem(COMPARE))
        assertThat(opTypes("a > b"), hasItem(COMPARE))
        assertThat(opTypes("a >= b"), hasItem(COMPARE))
        assertThat(opTypes("a == b"), hasItem(COMPARE))
        assertThat(opTypes("a != b"), hasItem(COMPARE))
    }

    @Test
    fun testInstanceOfOpType() {
        assertThat(opTypes("a instanceof b"), hasItem(INSTANCE_OF))
    }

    @Test
    fun testBinaryOpTypes() {
        assertThat(opTypes("a & b"), hasItem(BINARY))
        assertThat(opTypes("a | b"), hasItem(BINARY))
        assertThat(opTypes("a ^ b"), hasItem(BINARY))
    }

    @Test
    fun testAndOrOpTypes() {
        assertThat(opTypes("a && b"), hasItem(AND_OR))
        assertThat(opTypes("a || b"), hasItem(AND_OR))
    }

    @Test
    fun testTernaryOpType() {
        assertThat(opTypes("a > b ? a : b"), hasItem(TERNARY))
    }

    @Test
    fun testNullCoalescingOpType() {
        assertThat(opTypes("a ?? b "), hasItem(NULL_COALESCING))
    }

    @Test
    fun testClassExtractionOpType() {
        assertThat(opTypes("a.class"), hasItem(CLASS_EXTRACTION))
    }

    @Test
    fun testGroupingOpType() {
        assertThat(opTypes("(a + b)"), hasItem(GROUPING))
    }

    @Test
    fun testBracketOpType() {
        assertThat(opTypes("a[b]"), hasItem(BRACKET))
    }
}