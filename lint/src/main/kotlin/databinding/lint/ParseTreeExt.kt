package databinding.lint

import org.antlr.v4.runtime.tree.ParseTree

@Suppress("unused")
fun ParseTree.asStringTree() = asStringTree(tree = this) { "${it::class.java.simpleName} : ${it.text}" }

private fun asStringTree(prefix: String = "", tree: ParseTree, callback: (ParseTree) -> String): String {
    return if (tree.childCount == 0) {
        "$prefix${callback(tree)}\n"
    } else {
        val builder = StringBuilder()
        builder.append("$prefix${callback(tree)}\n")

        (0 until tree.childCount).forEach { i ->
            val str = asStringTree("$prefix  ", tree.getChild(i), callback)
            builder.append(str)
        }
        builder.toString()
    }
}