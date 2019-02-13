package databinding.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintResult
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test

class ExpressionDetectorTest {

    private fun lint(expression: String): TestLintResult {
        return TestLintTask.lint()
            .files(withExpression(expression))
            .issues(*ExpressionDetector.ISSUES.toTypedArray())
            .run()
    }

    private fun withExpression(expression: String): TestFile {
        return xml(
            "res/layout/example.xml",
            """
                |<?xml version="1.0" encoding="utf-8"?>
                |<TextView xmlns:android="http://schemas.android.com/apk/res/android"
                |    android:layout_width="wrap_content"
                |    android:layout_height="wrap_content"
                |    android:text="@{$expression}" />
            """.trimMargin()
        )
    }

    @Test
    fun testCastOperator() {
        lint("(A) a")
            .expect(
                """
                |res/layout/example.xml:5: Error: Cast operator in binding expression [CastOperator]
                |    android:text="@{(A) a}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testUnaryOperator() {
        lint("+a")
            .expect(
                """
                |res/layout/example.xml:5: Error: Unary operator in binding expression [UnaryOperator]
                |    android:text="@{+a}" />
                |    ~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testMathOperator() {
        lint("a + b")
            .expect(
                """
                |res/layout/example.xml:5: Error: Mathematical operator in binding expression [MathOperator]
                |    android:text="@{a + b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testBitShiftOperator() {
        lint("a >> b")
            .expect(
                """
                |res/layout/example.xml:5: Error: Bit-shift operator in binding expression [BitShiftOperator]
                |    android:text="@{a >> b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testCompareOperator() {
        lint("a > b")
            .expect(
                """
                |res/layout/example.xml:5: Error: Comparison operator in binding expression [CompareOperator]
                |    android:text="@{a > b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testInstanceOfOperator() {
        lint("a instanceof b")
            .expect(
                """
                |res/layout/example.xml:5: Error: InstanceOf operator in binding expression [InstanceOfOperator]
                |    android:text="@{a instanceof b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testBinaryOperator() {
        lint("a | b")
            .expect(
                """
                |res/layout/example.xml:5: Error: Binary operator in binding expression [BinaryOperator]
                |    android:text="@{a | b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testAndOrOperator() {
        lint("a || b")
            .expect(
                """
                |res/layout/example.xml:5: Error: AndOr operator in binding expression [AndOrOperator]
                |    android:text="@{a || b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testTernaryOperator() {
        lint("a > b ? a : b")
            .expect(
                """
                |res/layout/example.xml:5: Error: Comparison operator in binding expression [CompareOperator]
                |    android:text="@{a > b ? a : b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |res/layout/example.xml:5: Error: Ternary operator in binding expression [TernaryOperator]
                |    android:text="@{a > b ? a : b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |2 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testClassExtractionOperator() {
        lint("a.class")
            .expect(
                """
                |res/layout/example.xml:5: Error: Class Extraction operator in binding expression [ClassExtractionOperator]
                |    android:text="@{a.class}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testNullCoalescingOperator() {
        lint("a ?? b")
            .expect(
                """
                |res/layout/example.xml:5: Error: Null Coalescing operator in binding expression [NullCoalescingOperator]
                |    android:text="@{a ?? b}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testGroupOperator() {
        lint("(a)")
            .expect(
                """
                |res/layout/example.xml:5: Error: Group operator in binding expression [GroupOperator]
                |    android:text="@{(a)}" />
                |    ~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }

    @Test
    fun testBracketOperator() {
        lint("a[b]")
            .expect(
                """
                |res/layout/example.xml:5: Error: Bracket operator in binding expression [BracketOperator]
                |    android:text="@{a[b]}" />
                |    ~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                """.trimMargin()
            )
    }
}

