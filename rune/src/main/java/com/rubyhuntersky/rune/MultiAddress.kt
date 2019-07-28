package com.rubyhuntersky.rune

import io.ipfs.cid.Cid

data class MultiAddress(val cid: Cid) : Byter {

    val multibase: String get() = "base58btc"
    val cidVersion: String get() = "cidv0"
    val muticodec: String get() = "dag-pb"
    val multihash: String get() = cid.type.name

    override val bytes: ByteArray get() = cid.toBytes()

    companion object : Debyter<MultiAddress> {

        override fun debyte(byteArray: ByteArray, start: Int): MultiAddress {
            val readFrom = byteArray.sliceArray(start until byteArray.size)
            val cid = Cid.cast(readFrom)
            return MultiAddress(cid)
        }

        fun valueOf(string: String): MultiAddress = MultiAddress(Cid.decode(string))
    }
}