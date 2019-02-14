package databinding.lint

import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
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

    @Test
    fun testLiteralOpTypes() {
        assertThat(opTypes("1"), hasItem(LITERAL))
        assertThat(opTypes("0b1"), hasItem(LITERAL))
        assertThat(opTypes("01"), hasItem(LITERAL))
        assertThat(opTypes("0x1"), hasItem(LITERAL))
        assertThat(opTypes("1.1"), hasItem(LITERAL))
        assertThat(opTypes("true"), hasItem(LITERAL))
        assertThat(opTypes("'a'"), hasItem(LITERAL))
        assertThat(opTypes("\"abc\""), hasItem(LITERAL))
    }

    @Test
    fun testGlobalMethodOpTypes() {
        assertThat(opTypes("a()"), hasItem(GLOBAL_METHOD))
        assertThat(opTypes("a(x)"), hasItem(GLOBAL_METHOD))
    }

    @Test
    fun testMethodOpTypes() {
        assertThat(opTypes("a.b()"), hasItem(METHOD))
        assertThat(opTypes("a.b(x)"), hasItem(METHOD))
    }

    @Test
    fun testLambdaOpTypes() {
        assertThat(opTypes("() -> a.b()"), not(hasItem(METHOD)))
        assertThat(opTypes("(x) -> a.b(x)"), not(hasItem(METHOD)))
    }
}