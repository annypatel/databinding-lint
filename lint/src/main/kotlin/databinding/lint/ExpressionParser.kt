package databinding.lint

import android.databinding.parser.BindingExpressionBaseVisitor
import android.databinding.parser.BindingExpressionLexer
import android.databinding.parser.BindingExpressionParser
import android.databinding.parser.BindingExpressionParser.AndOrOpContext
import android.databinding.parser.BindingExpressionParser.BinaryOpContext
import android.databinding.parser.BindingExpressionParser.BitShiftOpContext
import android.databinding.parser.BindingExpressionParser.BracketOpContext
import android.databinding.parser.BindingExpressionParser.CastOpContext
import android.databinding.parser.BindingExpressionParser.ClassExtractionContext
import android.databinding.parser.BindingExpressionParser.ComparisonOpContext
import android.databinding.parser.BindingExpressionParser.GlobalMethodInvocationContext
import android.databinding.parser.BindingExpressionParser.GroupingContext
import android.databinding.parser.BindingExpressionParser.InstanceOfOpContext
import android.databinding.parser.BindingExpressionParser.LambdaExpressionContext
import android.databinding.parser.BindingExpressionParser.LiteralContext
import android.databinding.parser.BindingExpressionParser.MathOpContext
import android.databinding.parser.BindingExpressionParser.MethodInvocationContext
import android.databinding.parser.BindingExpressionParser.QuestionQuestionOpContext
import android.databinding.parser.BindingExpressionParser.TernaryOpContext
import android.databinding.parser.BindingExpressionParser.UnaryOpContext
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext

val CAST = OpType.of(CastOpContext::class)
val UNARY = OpType.of(UnaryOpContext::class)
val MATH = OpType.of(MathOpContext::class)
val BIT_SHIFT = OpType.of(BitShiftOpContext::class)
val COMPARE = OpType.of(ComparisonOpContext::class)
val INSTANCE_OF = OpType.of(InstanceOfOpContext::class)
val BINARY = OpType.of(BinaryOpContext::class)
val AND_OR = OpType.of(AndOrOpContext::class)
val TERNARY = OpType.of(TernaryOpContext::class)
val NULL_COALESCING = OpType.of(QuestionQuestionOpContext::class)
val CLASS_EXTRACTION = OpType.of(ClassExtractionContext::class)
val GROUPING = OpType.of(GroupingContext::class)
val BRACKET = OpType.of(BracketOpContext::class)
val LITERAL = OpType.of(LiteralContext::class)
val METHOD = OpType.of(MethodInvocationContext::class)
val GLOBAL_METHOD = OpType.of(GlobalMethodInvocationContext::class)

/**
 * Parser for data binding expressions.
 */
class ExpressionParser {

    private val visitor = Visitor()

    /**
     * Returns set of unique operation types present in the given binding expression.
     */
    fun parse(expression: String): Set<OpType> {
        return try {
            val input = ANTLRInputStream(expression)
            val lexer = BindingExpressionLexer(input)
            val tokens = CommonTokenStream(lexer)
            val parser = BindingExpressionParser(tokens)
            val context = parser.bindingSyntax()
            context.accept(visitor).toSet()
        } catch (_: Throwable) {
            emptySet()
        }
    }

    class Visitor : BindingExpressionListVisitor() {

        override fun visitCastOp(ctx: CastOpContext) = report(ctx)
        override fun visitUnaryOp(ctx: UnaryOpContext) = report(ctx)
        override fun visitMathOp(ctx: MathOpContext): List<OpType> = report(ctx)
        override fun visitBitShiftOp(ctx: BitShiftOpContext) = report(ctx)
        override fun visitComparisonOp(ctx: ComparisonOpContext) = report(ctx)
        override fun visitInstanceOfOp(ctx: InstanceOfOpContext) = report(ctx)
        override fun visitBinaryOp(ctx: BinaryOpContext) = report(ctx)
        override fun visitAndOrOp(ctx: AndOrOpContext) = report(ctx)
        override fun visitTernaryOp(ctx: TernaryOpContext) = report(ctx)
        override fun visitQuestionQuestionOp(ctx: QuestionQuestionOpContext) = report(ctx)
        override fun visitClassExtraction(ctx: ClassExtractionContext) = report(ctx)
        override fun visitGrouping(ctx: GroupingContext) = report(ctx)
        override fun visitBracketOp(ctx: BracketOpContext) = report(ctx)
        override fun visitLiteral(ctx: LiteralContext) = report(ctx)
        override fun visitGlobalMethodInvocation(ctx: GlobalMethodInvocationContext) = reportIfNotLambda(ctx)
        override fun visitMethodInvocation(ctx: MethodInvocationContext) = reportIfNotLambda(ctx)

        private fun reportIfNotLambda(ctx: ParserRuleContext): List<OpType> =
            if (ctx.parent is LambdaExpressionContext) {
                visitChildren(ctx)
            } else {
                report(ctx)
            }

        private fun report(ctx: ParserRuleContext): List<OpType> =
            mutableListOf(OpType.of(ctx)).apply {
                addAll(visitChildren(ctx))
            }
    }

    open class BindingExpressionListVisitor : BindingExpressionBaseVisitor<List<OpType>>() {

        final override fun defaultResult(): List<OpType> = emptyList()

        final override fun aggregateResult(aggregate: List<OpType>, nextResult: List<OpType>) = when {
            aggregate.isEmpty() -> nextResult
            nextResult.isEmpty() -> aggregate
            aggregate is MutableList -> aggregate.apply { addAll(nextResult) }
            else -> aggregate + nextResult
        }
    }
}
