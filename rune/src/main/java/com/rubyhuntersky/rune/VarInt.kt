package com.rubyhuntersky.rune

import java.lang.Integer.min
import java.math.BigInteger

data class VarInt(val value: BigInteger) : ByteArrayPrinter {
    constructor(long: Long) : this(BigInteger.valueOf(long))

    init {
        require(value >= BigInteger.ZERO) { "Value must be non-negative." }
        require(value.bitLength() <= bitLimit) { "Value must fit in 63 bits." }
    }

    override val bytes: ByteArray by lazy {
        val byteList = mutableListOf<Byte>()
        var rolling = value
        while (rolling > BigInteger.ZERO) {
            val lower7 = (0 until 7).map(rolling::testBit).foldRight(0, { set, result ->
                result.shl(1) or if (set) 0b1 else 0b0
            })
            rolling = rolling.shr(7)
            val all8 = lower7 or if (rolling > BigInteger.ZERO) 0b10000000 else 0b00000000
            byteList.add(all8.toSignedByte())
        }
        if (byteList.size == 0) {
            ByteArray(1)
        } else {
            byteList.toByteArray()
        }
    }

    companion object : ByteArrayParser<VarInt> {

        private const val byteLimit = 9
        private const val bitLimit = byteLimit * 7

        override fun read(byteArray: ByteArray, start: Int): VarInt {
            require(byteArray.size - start > 0) { "Too few bytes." }
            val maxValidIndex = start + byteLimit - 1
            val lastIndex = min(byteArray.lastIndex, maxValidIndex)
            var answer = BigInteger.ZERO
            for (i in start..lastIndex) {
                val byte = byteArray[i]
                val unsigned = byte.toIntUnsigned()
                val dataBits = unsigned and 0x7f
                answer += BigInteger.valueOf(dataBits.toLong()).shiftLeft(7 * (i - start))
                val moreBit = unsigned and 0x80
                if (moreBit == 0x00) break
                else check(i != lastIndex) {
                    if (i < maxValidIndex) "Too few bytes."
                    else "Too many significant bytes, limit 9."
                }
            }
            return VarInt(answer)
        }
    }
}