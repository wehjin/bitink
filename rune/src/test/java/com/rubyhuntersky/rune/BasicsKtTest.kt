package com.rubyhuntersky.rune

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BasicsKtTest {

    private val byteAndIntUnsigned = listOf(
        Pair(0x02.toByte(), 2),
        Pair(0x7f.toByte(), 127),
        Pair((-128).toByte(), 128),
        Pair((-1).toByte(), 255)
    )

    @Test
    internal fun byteToIntUnsigned() {
        byteAndIntUnsigned.forEach { (byte, intUnsigned) ->
            val n = byte.toIntUnsigned()
            assertEquals(intUnsigned, n)
        }
    }

    @Test
    internal fun intUnsignedToByte() {
        byteAndIntUnsigned.forEach { (byte, intUnsigned) ->
            val b = intUnsigned.toSignedByte()
            assertEquals(byte, b)
        }
    }
}