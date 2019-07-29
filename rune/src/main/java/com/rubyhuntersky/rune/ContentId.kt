package com.rubyhuntersky.rune

import io.ipfs.cid.Cid

data class ContentId(val cid: Cid) : Byter {

    val multibase: String get() = "base58btc"
    val cidVersion: String get() = "cidv0"
    val muticodec: String get() = "dag-pb"
    val multihash: String get() = cid.type.name

    override val bytes: ByteArray get() = cid.toBytes()

    companion object : Debyter<ContentId> {

        override fun debyte(byteArray: ByteArray, start: Int): ContentId {
            val readFrom = byteArray.sliceArray(start until byteArray.size)
            val cid = Cid.cast(readFrom)
            return ContentId(cid)
        }

        fun valueOf(string: String) = ContentId(Cid.decode(string))
    }
}