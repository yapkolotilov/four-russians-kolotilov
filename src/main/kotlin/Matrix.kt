import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log

/**
 * Boolean matrix.
 */
class Matrix(private val rows: Array<BooleanArray>) {

    companion object {

        /**
         * Creates new matrix with int array.
         *
         * @param rows Rows.
         */
        operator fun invoke(vararg rows: IntArray): Matrix {
            return Matrix(
                rows = rows.map { it.map { it.toBoolean() }.toBooleanArray() }.toTypedArray()
            )
        }

        /**
         * Creates new matrix of size (height, width) with generator function.
         *
         * @param height Height.
         * @param width Width.
         * @param generator Generator function.
         */
        inline operator fun invoke(
            height: Int,
            width: Int,
            generator: (i: Int, j: Int) -> Boolean = { _, _ -> false }
        ): Matrix {
            return Matrix(
                rows = Array(height) { i ->
                    BooleanArray(width) { j ->
                        generator(i, j)
                    }
                }
            )
        }
    }

    init {
        if (rows.isEmpty() || rows[0].isEmpty())
            throw IllegalArgumentException("Matrix cannot be empty!")
        val width = rows[0].size
        if (!rows.all { it.size == width })
            throw IllegalArgumentException("Wrong matrix size!")
    }

    /**
     * Height.
     */
    val height: Int get() = rows.size

    /**
     * Width.
     */
    val width: Int get() = rows[0].size

    /**
     * Returns item at (i,j) coordinates.
     *
     * @param i Row.
     * @param j Column.
     */
    operator fun get(i: Int, j: Int): Boolean = rows[i][j]

    /**
     * Returns item at (i,j) coordinates or null if indices are invalid.
     *
     * @param i Row.
     * @param j Column.
     */
    fun getOrNull(i: Int, j: Int): Boolean? = rows.getOrNull(i)?.getOrNull(j)

    /**
     * Performs addition operation.
     *
     * @param other Second matrix.
     */
    operator fun plus(other: Matrix): Matrix {
        val a = this
        val b = other
        if (a.width != b.width && a.height != b.height)
            throw IllegalArgumentException("A is size(${a.height}, ${a.width}), whereas B is size (${b.height}, ${b.width})!")
        return Matrix(a.height, a.width) { i, j ->
            a[i, j] + b[i, j]
        }
    }

    /**
     * Performs multiplication operation using standard algorithm.
     *
     * @param other Second matrix.
     */
    operator fun times(other: Matrix): Matrix {
        val a = this
        val b = other
        if (a.width != b.height)
            throw IllegalArgumentException("A is size(${a.height}, ${a.width}), whereas B is size (${b.height}, ${b.width})!")
        return Matrix(a.height, b.width) { i, j ->
            var result = false
            for (k in 0 until a.width)
                result += a[i, k] * b[k, j]
            result
        }
    }

    /**
     * Returns submatrix of this matrix.
     *
     * @param rows Rows range.
     * @param columns Columns range.
     */
    fun subMatrix(rows: IntRange, columns: IntRange): Matrix {
        return Matrix(
            rows = this.rows.subArray(rows, columns)
        )
    }

    /**
     * Fills zeros to size.
     *
     * @param height Height.
     * @param width Width.
     */
    fun fillZeros(height: Int, width: Int): Matrix {
        if (this.height == height && this.width == width)
            return this

        return Matrix(height, width) { i, j ->
            getOrNull(i, j) ?: false
        }
    }

    /**
     * Performs multiplication operation using 4 russians algorithm.
     *
     * @param other Second matrix.
     */
    infix fun fourRussians(other: Matrix): Matrix {
        val a = this
        val b = other
        val n = a.width
        if (a.height != n && b.height != n && b.width != n)
            throw IllegalArgumentException("A and B must be square matrices of same size!")
        if (n == 1)
            return a * b
        val m = ceil(n / log(n.toDouble(), 2.0)).toInt()
        val lgN = floor(log(n.toDouble(), 2.0)).toInt()

        fun firstIndex(index: Int): Int {
            val i = index + 1
            return (m - 1) * (i - 1)
        }
        fun lastIndex(index: Int): Int {
            val i = index + 1
            return if (i == m) a.width - 1 else (m - 1) * i - 1
        }
        val aParts = List(m) {
            val firstIndex = firstIndex(it)
            val lastIndex = lastIndex(it)
            a.subMatrix(0 until a.height, firstIndex..lastIndex).fillZeros(n, lgN)
        }
        val bParts = List(m) {
            val firstIndex = firstIndex(it)
            val lastIndex = lastIndex(it)
            b.subMatrix(firstIndex..lastIndex, 0 until a.height).fillZeros(lgN, n)
        }
        var result = Matrix(n, n)
        for (i in 0 until m)
            result += aParts[i] * bParts[i]

        return a * b
    }

    override fun toString(): String {
        return rows.joinToString("\n") { it.joinToString { it.toInt().toString() } }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (!rows.contentDeepEquals(other.rows)) return false

        return true
    }

    override fun hashCode(): Int {
        return rows.contentDeepHashCode()
    }

    private fun Array<BooleanArray>.subArray(rows: IntRange, columns: IntRange): Array<BooleanArray> {
        return Array(rows.last - rows.first + 1) { this[rows.first + it].subArray(columns) }
    }
}