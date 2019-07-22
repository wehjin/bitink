package com.rubyhuntersky.chain.basics

import com.rubyhuntersky.chain.block.BlockElement
import java.math.BigInteger

data class VarInt(val value: BigInteger) : BlockElement {
    constructor(long: Long) : this(BigInteger.valueOf(long))

    init {
        require(value >= BigInteger.ZERO) { "Value must be non-negative." }
        require(value.bitLength() <= 63) { "Value must fit in 63 bits." }
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
            byteList.add((all8 - 256).toByte())
        }
        if (byteList.size == 0) {
            ByteArray(1)
        } else {
            byteList.toByteArray()
        }
    }
}