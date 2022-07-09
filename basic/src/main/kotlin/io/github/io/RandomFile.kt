package io.github.io

import java.io.*
import java.nio.*
import java.nio.channels.*
import kotlin.text.Charsets.UTF_8

fun main() {

}


fun useRandomAccessFileMapMethod() {
    RandomAccessFile("test.txt", "rw").use {
        it.channel.use {
            it.map(FileChannel.MapMode.READ_WRITE, 0, 16).let {
                println(it.getLong(0))
                println(it.getLong(8))
            }
        }
    }
}


fun useReadMethod() {
    RandomAccessFile("test.txt", "rw").use {
        it.channel.use {
            val allocate = ByteBuffer.allocate(10)
            it.read(allocate)
            allocate.flip()
            while (allocate.hasRemaining()) {
                print(allocate.get().toInt().toChar())
            }
            it.force(true)
            println(String(allocate.array(), allocate.arrayOffset(), allocate.position(), UTF_8))
            println(String(allocate.array(), allocate.arrayOffset(), allocate.position(), UTF_8))
        }
    }
}
