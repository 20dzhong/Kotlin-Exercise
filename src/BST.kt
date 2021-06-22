class Node(var value: Int, var left: Node? = null, var right: Node? = null)

class BST {
    private var root: Node? = null

    // insert helper
    private fun insertRecursive(curr: Node? = null, value: Int): Node {
        when {
            (curr == null) -> return Node(value)
            (value < curr.value) -> curr.left = insertRecursive(curr.left, value)
            (value > curr.value) -> curr.right = insertRecursive(curr.right, value)
            else -> curr
        }
        return curr
    }

    // insert
    fun insert(value: Int) {
        root = insertRecursive(root, value)
    }

    // search helper
    private fun containRecursive(curr: Node? = null, value: Int): Boolean {
        when {
            (curr == null) -> return false
            (value == curr.value) -> return true
            else -> return if (value < curr.value) containRecursive(curr.left, value) else containRecursive(
                curr.right,
                value
            )
        }
    }

    // search
    fun contain(value: Int): Boolean = containRecursive(root, value)

    // find minValue in a branch for delete recursive
    private fun minValue(curr: Node): Int = (if (curr.left == null) curr.value else minValue(curr.left!!))

    // delete helper, a bit of a mess here
    private fun deleteRecursive(curr: Node? = null, value: Int): Node? {
        when {
            (curr == null) -> return null

            (value == curr.value) -> when {
                (curr.left == null && curr.right == null) -> return null
                (curr.right == null) -> return curr.left
                (curr.left == null) -> return curr.right
                else -> {
                    var tmpVal = minValue(curr.right!!)
                    curr.value = tmpVal
                    curr.right = deleteRecursive(curr.right, tmpVal)
                    return curr
                }
            }
            (value < curr.value) -> {
                curr.left = deleteRecursive(curr.left, value)
                return curr
            }
            (value > curr.value) -> {
                curr.right = deleteRecursive(curr.right, value)
                return curr
            }
        }
        return curr
    }

    // delete
    fun delete(value: Int) {
        root = deleteRecursive(root, value)
    }
}

fun main(args: Array<String>) {
    val bstTest = BST()

    bstTest.insert(5)
    bstTest.insert(3)
    bstTest.insert(7)
    bstTest.insert(9)
    bstTest.insert(10)
    bstTest.insert(15)
    bstTest.insert(2)
    bstTest.insert(1)
    bstTest.insert(0)

    println(bstTest.contain(7))
    println(bstTest.contain(0))
    println(bstTest.contain(15))

    bstTest.delete(7)
    println(bstTest.contain(7))
    bstTest.delete(0)
    println(bstTest.contain(0))
    bstTest.delete(5)
    println(bstTest.contain(2))
}