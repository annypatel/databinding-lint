package databinding.lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.TextFormat
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScannerConstants
import org.w3c.dom.Attr

/**
 * Detects usage of various operations in Data Binding expression. Refer [issues][ExpressionDetector.issues] for list
 * of supported operations.
 */
class ExpressionDetector : LayoutDetector() {

    companion object {

        private val issues = mapOf(
            CAST to issue("CastOperator", "Cast operator"),
            UNARY to issue("UnaryOperator", "Unary operator"),
            MATH to issue("MathOperator", "Mathematical operator"),
            BIT_SHIFT to issue("BitShiftOperator", "Bit-shift operator"),
            COMPARE to issue("CompareOperator", "Comparison operator"),
            INSTANCE_OF to issue("InstanceOfOperator", "InstanceOf operator"),
            BINARY to issue("BinaryOperator", "Binary operator"),
            AND_OR to issue("AndOrOperator", "AndOr operator"),
            TERNARY to issue("TernaryOperator", "Ternary operator"),
            NULL_COALESCING to issue("NullCoalescingOperator", "Null Coalescing operator"),
            CLASS_EXTRACTION to issue("ClassExtractionOperator", "Class Extraction operator"),
            GROUPING to issue("GroupOperator", "Group operator"),
            BRACKET to issue("BracketOperator", "Bracket operator")
        )

        private fun issue(id: String, name: String) = Issue.create(
            id,
            "$name in binding expression",
            "Defining complex logic as part of data binding expression is not \n" +
                "recommended. Consider using the View Model or Data class in such scenario.",
            Category.CORRECTNESS,
            10,
            Severity.ERROR,
            Implementation(
                ExpressionDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )

        val ISSUES = issues.values.toList()
    }

    private val parser = ExpressionParser()

    override fun getApplicableAttributes(): Collection<String>? {
        return XmlScannerConstants.ALL
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        if (!attribute.value.isBinding()) {
            return
        }

        val expression = attribute.value.escapeBinding()
        val types = parser.parse(expression)

        types.forEach { type ->

            issues[type]?.let {
                val location = context.getLocation(attribute)
                val message = it.getBriefDescription(TextFormat.TEXT)
                context.report(it, location, message)
            }
        }
    }
}