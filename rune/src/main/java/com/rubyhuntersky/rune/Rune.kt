package com.rubyhuntersky.rune

import io.ipfs.cid.Cid

sealed class Rune(private val subTypeId: Int, private val subTypePrinter: Byter) : Byter {

    override val bytes: ByteArray by lazy { VarInt(subTypeId.toLong()).bytes + subTypePrinter.bytes }

    data class Name(val socialName: SocialName) : Rune(1, socialName) {
        companion object {

            fun valueOf(nameString: String) = Name(SocialName(nameString))
        }
    }

    data class Noid(val contentId: ContentId) : Rune(2, contentId) {
        companion object {

            fun valueOf(cidString: String) = Noid(ContentId(Cid.decode(cidString)))
        }
    }

    data class Number(val varInt: VarInt) : Rune(3, varInt) {
        companion object {

            fun valueOf(long: Long) = Number(VarInt.valueOf(long))
        }
    }
}
