class Node(var value: Int, var left: Node? = null, var right: Node? = null)

class BST {
    private var root: Node? = null

    // insert helper
    private fun insertRecursive(curr: Node? = null, value: Int): Node =
        when {
            (curr == null) -> Node(value)
            (value < curr.value) -> curr.apply { left = insertRecursive(left, value) }
            (value > curr.value) -> curr.apply { right = insertRecursive(right, value) }
            else -> curr
        }

    // insert
    operator fun plus(value: Int) = this.apply { root = insertRecursive(root, value) }

    // search helper
    private fun containRecursive(curr: Node? = null, value: Int): Boolean =
        when {
            (curr == null) -> false
            (value == curr.value) -> true
            else -> containRecursive(if (value < curr.value) curr.left else curr.right, value)
        }

    // search
    operator fun contains(value: Int): Boolean = containRecursive(root, value)

    // find minValue in a branch for delete recursive
    private fun minValue(curr: Node): Int = (if (curr.left == null) curr.value else minValue(curr.left!!))

    // delete helper, a bit of a mess here
    private fun deleteRecursive(curr: Node? = null, value: Int): Node? =
        when {
            (curr == null) -> null
            (value == curr.value) ->
                when {
                    (curr.left == null && curr.right == null) -> null
                    (curr.right == null) -> curr.left
                    (curr.left == null) -> curr.right
                    else -> {
                        curr.also {
                            minValue(it.right!!).also { tmpVal ->
                                it.value = tmpVal
                                it.right = deleteRecursive(it.right, tmpVal)
                            }
                        }
                    }
                }
            (value < curr.value) -> curr.apply { left = deleteRecursive(left, value) }
            (value > curr.value) -> curr.apply { right = deleteRecursive(right, value) }
            else -> curr
        }

    // delete
    operator fun minus(value: Int) = apply { root = deleteRecursive(root, value) }
}

fun main(args: Array<String>) {
    val bstTest = BST() + 5 + 3 + 7 + 9 + 10 + 15 + 2 + 1 + 0

    println(7 in bstTest)
    println(0 in bstTest)
    println(15 in bstTest)

    bstTest - 7 - 0 - 5
    println(7 in bstTest)
    println(0 in bstTest)
    println(2 in bstTest)
}
