fun main() {
    val a = Matrix(
        intArrayOf(1, 1, 0, 0),
        intArrayOf(0, 0, 1, 1),
        intArrayOf(0, 1, 0, 1),
        intArrayOf(0, 1, 0, 1),
    )
    val b = Matrix(
        intArrayOf(0, 1, 0, 0),
        intArrayOf(0, 0, 1, 0),
        intArrayOf(1, 1, 0, 1),
        intArrayOf(1, 0, 1, 0),
    )
    println(a * b)
    println()
    println(a fourRussians b)
}