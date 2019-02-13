package databinding.lint

import kotlin.reflect.KClass

/**
 * Represents type of operator in the data binding expression.
 */
data class OpType(val type: String) {

    companion object {
        fun of(type: KClass<*>) = OpType(type = type.java.simpleName)
        fun of(type: Any) = of(type::class)
    }
}