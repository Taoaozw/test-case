import sun.misc.Unsafe

val UNSAFE = Unsafe::class.java.getDeclaredField("theUnsafe").apply { isAccessible = true }.get(null) as Unsafe

fun main() {
    test()
}

fun test() {
    val arrayTest = SafeArray()
    arrayTest[0] = 10
    println(arrayTest[0])
}

class SafeArray : IntArrayAccess {
    override val arr = IntArray(10)

    override val offset = intArrayOffset()
    override val shift = intArrayShift()

    override operator fun get(index: Int): Int = arr.getViolate(index)

    override operator fun set(index: Int, value: Int) = arr.putOrdered(index, value)
}

interface IntArrayAccess {
    val arr: IntArray
    val offset: Int
    val shift: Int
    operator fun get(index: Int): Int
    operator fun set(index: Int, value: Int)
}

fun intArrayOffset() = UNSAFE.arrayBaseOffset(IntArray::class.java)

/**
 * 31  = 1.countLeadingZeroBits() 代表1 前面还有几个0
 * 所以这个得出的是从1 过去的偏移量
 */
fun intArrayShift() = 31 - UNSAFE.arrayIndexScale(IntArray::class.java).countLeadingZeroBits()

context(IntArrayAccess)
fun IntArray.putOrdered(index: Int, value: Int) {
    UNSAFE.putOrderedObject(this, (offset + index shl shift).toLong(), value)
}

context(IntArrayAccess)
fun IntArray.getViolate(index: Int): Int {
    return UNSAFE.getObjectVolatile(this, (offset + index shl shift).toLong()) as Int
}
