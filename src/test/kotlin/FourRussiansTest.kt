import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class FourRussiansTest(
    private val a: Matrix,
    private val b: Matrix
) {

    companion object {

        private data class Params(
            val a: Matrix,
            val b: Matrix
        ) {
            fun toArray(): Array<Any> = arrayOf(a, b)
        }

        @Parameterized.Parameters
        @JvmStatic
        fun matrices(): Iterable<Array<Any>> {
            return listOf(
                Params(
                    a = Matrix(
                        intArrayOf(1, 1, 0, 0, 0),
                        intArrayOf(0, 0, 1, 1, 1),
                        intArrayOf(1, 0, 0, 1, 0),
                        intArrayOf(1, 0, 0, 1, 1),
                        intArrayOf(1, 0, 1, 0, 1)
                    ),
                    b = Matrix(
                        intArrayOf(0, 1, 0, 0, 1),
                        intArrayOf(0, 0, 0, 0, 0),
                        intArrayOf(1, 1, 0, 0, 1),
                        intArrayOf(1, 0, 1, 0, 0),
                        intArrayOf(1, 1, 0, 1, 0)
                    )
                ).toArray(),

                Params(
                    a = Matrix(
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1)
                    ),
                    b = Matrix(
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1),
                        intArrayOf(1, 1, 1, 1, 1, 1)
                    )
                ).toArray(),

                Params(
                    a = Matrix(
                        intArrayOf(1, 0),
                        intArrayOf(1, 1)
                    ),
                    b = Matrix(
                        intArrayOf(1, 1),
                        intArrayOf(0, 0)
                    )
                ).toArray()
            )
        }
    }

    @Test
    fun test() {
        assertEquals(a * b, a.fourRussians(b))
    }
}