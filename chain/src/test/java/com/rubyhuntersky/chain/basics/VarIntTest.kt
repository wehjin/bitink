package com.rubyhuntersky.chain.basics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigInteger
import kotlin.math.pow

object VarIntTest : Spek({

    describe("VarInt") {

        it("requires a non-negative value to construct") {
            val negativeValue = BigInteger.valueOf(-1)
            val exception = assertThrows<Throwable> { VarInt(negativeValue) }
            assertEquals("Value must be non-negative.", exception.localizedMessage)

            val zeroValue = BigInteger.ZERO
            assertNotNull(VarInt(zeroValue))
        }

        it("requires a 63 bit or lower value to construct") {
            val tooHighValue = BigInteger.valueOf(2).pow(63)
            val topValue = tooHighValue - BigInteger.ONE

            assertNotNull(VarInt(topValue))

            val exception = assertThrows<Throwable> { VarInt(tooHighValue) }
            assertEquals("Value must fit in 63 bits.", exception.localizedMessage)
        }

        it("provides a one-byte array for values under 8 bits long") {
            (0 until 128).forEach {
                val varint = VarInt(it.toLong())
                val bytes = varint.bytes
                assertEquals(1, bytes.size)
                assertEquals(it.toByte(), bytes[0])
            }
        }

        it("provides a two-byte array for values between 8 and 14 bits long") {
            (128 until 16384).forEach {
                val varint = VarInt(it.toLong())
                val bytes = varint.bytes
                assertEquals(2, bytes.size)

                val byte0 = bytes[0].toIntUnsigned()
                assertEquals(it and 0x7f, byte0 and 0x7f)
                assertEquals(0x80, byte0 and 0x80)

                val byte1 = bytes[1].toIntUnsigned()
                assertEquals(it.shr(7) and 0x7f, byte1 and 0x7f)
                assertEquals(0x00, byte1 and 0x80)
            }
        }

        it("provides a three-byte array for values between 14 and 21 bits long") {
            (14 until 21).map { 2.0.pow(it.toDouble()).toInt() }
                .toMutableList().also { it.add((it.last() - 1) * 2 - 1) }
                .forEach {
                    val varint = VarInt(it.toLong())
                    val bytes = varint.bytes
                    assertEquals(3, bytes.size, "it: ${Integer.toHexString(it)}")

                    val byte0 = bytes[0].toIntUnsigned()
                    assertEquals(it and 0x7f, byte0 and 0x7f)
                    assertEquals(0x80, byte0 and 0x80)

                    val byte1 = bytes[1].toIntUnsigned()
                    assertEquals(it.shr(7) and 0x7f, byte1 and 0x7f)
                    assertEquals(0x80, byte1 and 0x80)

                    val byte2 = bytes[2].toIntUnsigned()
                    assertEquals(it.shr(14) and 0x7f, byte2 and 0x7f)
                    assertEquals(0x00, byte2 and 0x80)
                }
        }

        it("provides a byte array that is (bit-length + 6)/7 bytes long") {
            (1 until 63).map { BigInteger.valueOf(2).pow(it) }
                .forEach { value ->
                    val varint = VarInt(value)
                    val bytes = varint.bytes
                    assertEquals((value.bitLength() + 6) / 7, bytes.size, "value: 0x${value.toString(16)}")
                    bytes.map { it.toIntUnsigned() }
                        .forEachIndexed { index, it ->
                            val expected = if (index < bytes.size - 1) 0x80 else 0x00
                            assertEquals(expected, it and 0x80)
                        }
                }
        }

        it("reads its value from a byte array") {

        }
    }
})