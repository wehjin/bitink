package com.rubyhuntersky.rune

import com.rubyhuntersky.rune.basics.things.VarInt
import com.rubyhuntersky.rune.basics.toHex
import com.rubyhuntersky.rune.basics.toSignedByteArray
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger

internal class VarIntTest {

    private val zeroEncoded = intArrayOf(0b00000000).toSignedByteArray()
    private val oneEncoded = intArrayOf(0b00000001).toSignedByteArray()
    private val oneByteHighLimitEncoded = intArrayOf(0b01111111).toSignedByteArray()
    private val twoByteLowLimitEncoded = intArrayOf(0b10000000, 0b00000001).toSignedByteArray()
    private val twoByteFirstByteFullEncoded = intArrayOf(0b11111111, 0b00000001).toSignedByteArray()
    private val twoByteHighLimitEncoded = intArrayOf(0b11111111, 0b01111111).toSignedByteArray()
    private val threeByteLowLimitEncoded = intArrayOf(0b10000000, 0b10000000, 0b00000001).toSignedByteArray()
    private val threeByteHighLimitEncoded = intArrayOf(0b11111111, 0b11111111, 0b01111111).toSignedByteArray()
    private val nineByteHighLimitEncoded =
        (0..7).map { 0b11111111 }.toMutableList().apply { add(0b01111111) }.toSignedByteArray()
    val tooManyBytesEncoded =
        (0..8).map { 0b10000000 }.toMutableList().apply { add(0b00000001) }.toSignedByteArray()

    private val tooHighDecoded = BigInteger.valueOf(2).pow(63)
    private val nineByteHighLimitDecoded = tooHighDecoded - BigInteger.ONE
    private val threeByteHighLimitDecoded = BigInteger.valueOf(2097151)
    private val threeByteLowLimitDecoded = BigInteger.valueOf(16384)
    private val twoByteHighLimitDecoded = BigInteger.valueOf(16383)
    private val twoByteFirstByteFullDecoded = BigInteger.valueOf(255)
    private val twoByteLowLimitDecoded = BigInteger.valueOf(128)
    private val oneByteHighLimitDecoded = BigInteger.valueOf(127)
    private val oneDecoded = BigInteger.ONE
    private val zeroDecoded = BigInteger.ZERO

    val allEncodedDecoded = listOf(
        Triple(zeroEncoded, zeroDecoded, "zero")
        , Triple(oneEncoded, oneDecoded, "one")
        , Triple(oneByteHighLimitEncoded, oneByteHighLimitDecoded, "oneByteHighLimit")
        , Triple(twoByteLowLimitEncoded, twoByteLowLimitDecoded, "twoByteLowLimit")
        , Triple(twoByteFirstByteFullEncoded, twoByteFirstByteFullDecoded, "twoByteFirstByteFull")
        , Triple(twoByteHighLimitEncoded, twoByteHighLimitDecoded, "twoByteHighLimit")
        , Triple(threeByteLowLimitEncoded, threeByteLowLimitDecoded, "threeByteLowLimit")
        , Triple(threeByteHighLimitEncoded, threeByteHighLimitDecoded, "threeByteHighLimit")
        , Triple(nineByteHighLimitEncoded, nineByteHighLimitDecoded, "nineByteHighLimit")
    )

    @Nested
    inner class Reading {
        @Test
        internal fun ignoresInsignificantBytes() {

            val tests = listOf(
                Triple(intArrayOf(0b00000000, 0x42).toSignedByteArray(), 0, BigInteger.ZERO)
                , Triple(intArrayOf(0x42, 0b00000000).toSignedByteArray(), 1, BigInteger.ZERO)
                , Triple(intArrayOf(0x42, 0b00000000, 0x42).toSignedByteArray(), 1, BigInteger.ZERO)
            )
            tests.forEach { (testArray, start, expected) ->
                val varint = VarInt.debyte(testArray, start)
                assertEquals(expected, varint.value) { "Input: ${testArray.toHex()}" }
            }
        }

        @Test
        internal fun acceptsArraysWithVaryingLengthsOfSignificantBytes() {

            allEncodedDecoded.forEach { (testArray, expectedValue, name) ->
                val varint = VarInt.debyte(testArray, 0)
                assertEquals(expectedValue, varint.value) { name }
            }
        }

        @Test
        internal fun rejectsArraysWithMoreThanNineSignificantBytes() {

            val exception = assertThrows<Throwable> { VarInt.debyte(tooManyBytesEncoded) }
            assertEquals("Too many significant bytes, limit 9.", exception.localizedMessage)
        }

        @Test
        internal fun rejectsEmptyArrays() {

            val exception = assertThrows<Throwable> { VarInt.debyte(ByteArray(0)) }
            assertEquals("Too few bytes.", exception.localizedMessage)
        }

        @Test
        internal fun rejectsArraysThatEndWithoutTerminating() {
            val testArray = threeByteHighLimitEncoded.sliceArray(0 until threeByteHighLimitEncoded.lastIndex)
            val exception = assertThrows<Throwable> { VarInt.debyte(testArray) }
            assertEquals("Too few bytes.", exception.localizedMessage)
        }
    }

    @Nested
    inner class Writing {

        @Test
        internal fun writesByteArraysOfVaryingLengths() {

            allEncodedDecoded.forEach { (expectedArray, testValue, name) ->
                val varint = VarInt(testValue)
                assertTrue(expectedArray.contentEquals(varint.bytes)) { name }
            }
        }

    }

    @Nested
    inner class Building {

        @Test
        internal fun rejectsNegativeValues() {

            val negativeValue = BigInteger.valueOf(-1)
            val exception = assertThrows<Throwable> { VarInt(negativeValue) }
            assertEquals("Value must be non-negative.", exception.localizedMessage)
        }

        @Test
        internal fun acceptsZeroValue() {

            assertNotNull(VarInt(BigInteger.ZERO))
        }

        @Test
        internal fun acceptsValuesAtTheHighLimit() {

            val varInt = VarInt(nineByteHighLimitDecoded)
            assertNotNull(varInt)
        }

        @Test
        internal fun rejectsValuesAboveTheHighLimit() {

            val exception = assertThrows<Throwable> { VarInt(tooHighDecoded) }
            assertEquals("Value must fit in 63 bits.", exception.localizedMessage)
        }

        @Test
        internal fun acceptsLong() {

            assertNotNull(VarInt(33L))
        }
    }
}