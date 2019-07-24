package com.rubyhuntersky.chain.basics

import com.rubyhuntersky.chain.block.BlockElement
import com.rubyhuntersky.chain.block.BlockElementReader
import io.ipfs.cid.Cid

data class ContentId(val cid: Cid) : BlockElement {

    val multibase: String get() = "base58btc"
    val cidVersion: String get() = "cidv0"
    val muticodec: String get() = "dag-pb"
    val multihash: String get() = cid.type.name


    override val bytes: ByteArray get() = cid.toBytes()

    companion object : BlockElementReader<ContentId> {

        override fun read(byteArray: ByteArray, start: Int): ContentId {
            val readFrom = byteArray.sliceArray(start until byteArray.size)
            val cid = Cid.cast(readFrom)
            return ContentId(cid)
        }
    }
}