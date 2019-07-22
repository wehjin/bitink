package com.rubyhuntersky.chain.basics

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigInteger

object VarIntTest : Spek({

    describe("VarInt") {

        val zeroEncoded = intArrayOf(0b00000000).toSignedByteArray()
        val oneEncoded = intArrayOf(0b00000001).toSignedByteArray()
        val oneByteHighLimitEncoded = intArrayOf(0b01111111).toSignedByteArray()
        val twoByteLowLimitEncoded = intArrayOf(0b10000000, 0b00000001).toSignedByteArray()
        val twoByteFirstByteFullEncoded = intArrayOf(0b11111111, 0b00000001).toSignedByteArray()
        val twoByteHighLimitEncoded = intArrayOf(0b11111111, 0b01111111).toSignedByteArray()
        val threeByteLowLimitEncoded = intArrayOf(0b10000000, 0b10000000, 0b00000001).toSignedByteArray()
        val threeByteHighLimitEncoded = intArrayOf(0b11111111, 0b11111111, 0b01111111).toSignedByteArray()
        val nineByteHighLimitEncoded =
            (0..7).map { 0b11111111 }.toMutableList().apply { add(0b01111111) }.toSignedByteArray()
        val tooManyBytesEncoded =
            (0..8).map { 0b10000000 }.toMutableList().apply { add(0b00000001) }.toSignedByteArray()

        val tooHighDecoded = BigInteger.valueOf(2).pow(63)
        val nineByteHighLimitDecoded = tooHighDecoded - BigInteger.ONE
        val threeByteHighLimitDecoded = BigInteger.valueOf(2097151)
        val threeByteLowLimitDecoded = BigInteger.valueOf(16384)
        val twoByteHighLimitDecoded = BigInteger.valueOf(16383)
        val twoByteFirstByteFullDecoded = BigInteger.valueOf(255)
        val twoByteLowLimitDecoded = BigInteger.valueOf(128)
        val oneByteHighLimitDecoded = BigInteger.valueOf(127)
        val oneDecoded = BigInteger.ONE
        val zeroDecoded = BigInteger.ZERO

        val allEncodedDecoded = listOf(
            Triple(zeroEncoded, zeroDecoded, "zero")
            , Triple(oneEncoded, oneDecoded, "one")
            , Triple(oneByteHighLimitEncoded, oneByteHighLimitDecoded, "oneByteHighLimit")
            , Triple(twoByteLowLimitEncoded, twoByteLowLimitDecoded, "twoByteLowLimit")
            , Triple(twoByteFirstByteFullEncoded, twoByteFirstByteFullDecoded, "twoByteFirstByteFull")
            , Triple(twoByteHighLimitEncoded, twoByteHighLimitDecoded, "twoByteHighLimit")
            , Triple(threeByteLowLimitEncoded, threeByteLowLimitDecoded, "threeByteLowLimit")
            , Triple(threeByteHighLimitEncoded, threeByteHighLimitDecoded, "threeByteHighLimit")
            , Triple(nineByteHighLimitEncoded, nineByteHighLimitDecoded, "ninByteHighLimit")
        )

        describe("reading") {

            it("ignores insignificant bytes") {
                val tests = listOf(
                    Triple(intArrayOf(0b00000000, 0x42).toSignedByteArray(), 0, BigInteger.ZERO)
                    , Triple(intArrayOf(0x42, 0b00000000).toSignedByteArray(), 1, BigInteger.ZERO)
                    , Triple(intArrayOf(0x42, 0b00000000, 0x42).toSignedByteArray(), 1, BigInteger.ZERO)
                )
                tests.forEach { (testArray, start, expected) ->
                    val varint = VarInt.read(testArray, start)
                    assertEquals(expected, varint.value) { "Input: ${testArray.toHex()}" }
                }
            }

            it("accepts arrays with varying runs of significant bytes") {
                allEncodedDecoded
                    .forEach { (testArray, expectedValue, name) ->
                        val varint = VarInt.read(testArray, 0)
                        assertEquals(expectedValue, varint.value) { name }
                    }
            }

            it("rejects arrays with more than 9 significant bytes") {
                val exception = assertThrows<Throwable> { VarInt.read(tooManyBytesEncoded) }
                assertEquals("Too many significant bytes, limit 9.", exception.localizedMessage)
            }
        }

        describe("writing") {

            it("accepts values of varying lengths") {
                allEncodedDecoded
                    .forEach { (expectedArray, testValue, name) ->
                        val varint = VarInt(testValue)
                        assertTrue(expectedArray.contentEquals(varint.bytes)) { name }
                    }
            }

            it("rejects negative values") {
                val negativeValue = BigInteger.valueOf(-1)
                val exception = assertThrows<Throwable> { VarInt(negativeValue) }
                assertEquals("Value must be non-negative.", exception.localizedMessage)
            }

            it("accepts zero value") {
                val varInt = VarInt(BigInteger.ZERO)
                assertNotNull(varInt)
            }

            it("rejects values above the high limit") {
                val exception = assertThrows<Throwable> { VarInt(tooHighDecoded) }
                assertEquals("Value must fit in 63 bits.", exception.localizedMessage)
            }

            it("accepts values at the high limit") {
                val varInt = VarInt(nineByteHighLimitDecoded)
                assertNotNull(varInt)
            }
        }
    }
})