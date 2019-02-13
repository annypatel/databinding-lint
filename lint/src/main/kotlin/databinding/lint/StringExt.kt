package databinding.lint

/**
 * Returns true if string is binding expression else false.
 */
fun String.isBinding(): Boolean {
    val isOneWay = startsWith("@{")
    val isTwoWay = startsWith("@={")
    val endBracket = endsWith('}')
    return (isOneWay || isTwoWay) && endBracket
}

/**
 * Removes binding syntax from string.
 */
fun String.escapeBinding(): String {
    val isTwoWay = startsWith("@={")
    val start = if (isTwoWay) 3 else 2
    val end = length - 1
    return substring(start, end)
}