package com.rubyhuntersky.rune

import io.ipfs.cid.Cid

data class ContentId(val cid: Cid) : ByteArrayPrinter {

    val multibase: String get() = "base58btc"
    val cidVersion: String get() = "cidv0"
    val muticodec: String get() = "dag-pb"
    val multihash: String get() = cid.type.name

    override val bytes: ByteArray get() = cid.toBytes()

    companion object : ByteArrayParser<ContentId> {

        override fun read(byteArray: ByteArray, start: Int): ContentId {
            val readFrom = byteArray.sliceArray(start until byteArray.size)
            val cid = Cid.cast(readFrom)
            return ContentId(cid)
        }
    }
}