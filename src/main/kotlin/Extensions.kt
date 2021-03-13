/**
 * Returns subarray.
 *
 * @param indices Indices.
 */
fun BooleanArray.subArray(indices: IntRange): BooleanArray {
    return BooleanArray(indices.last - indices.first + 1) {
        this[indices.first + it]
    }
}

/**
 * Boolean multiplication.
 *
 * @param other Second boolean.
 */
operator fun Boolean.times(other: Boolean): Boolean {
    return this && other
}

/**
 * Boolean adding.
 *
 * @param other Second boolean.
 */
operator fun Boolean.plus(other: Boolean): Boolean {
    return this || other
}

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean() = when(this) {
    0 -> false
    1 -> true
    else -> throw IllegalArgumentException()
}