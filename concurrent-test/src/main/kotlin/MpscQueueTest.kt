import MpscQueueTest.MPSC_ARRAY_QUEUE
import org.jctools.queues.*

object MpscQueueTest{
    val MPSC_ARRAY_QUEUE: MpscArrayQueue<String> = MpscArrayQueue(2)
}

fun main(args: Array<String>) {
    for (i in 1..2) {
        Thread({ MPSC_ARRAY_QUEUE.offer("data$i") }, "thread$i").start()
    }
    try {
        Thread.sleep(1000L)
        MPSC_ARRAY_QUEUE.add("data3") // 入队操作，队列满则抛出异常
    } catch (e: Exception) {
        e.printStackTrace()
    }
    println("队列大小：" + MPSC_ARRAY_QUEUE.size + ", 队列容量：" + MPSC_ARRAY_QUEUE.capacity())
    println("出队：" + MPSC_ARRAY_QUEUE.remove()) // 出队操作，队列为空则抛出异常
    println("出队：" + MPSC_ARRAY_QUEUE.poll()) // 出队操作，队列为空则返回 NULL
}