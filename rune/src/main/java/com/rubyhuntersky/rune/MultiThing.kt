package com.rubyhuntersky.rune

sealed class MultiThing(private val subTypeId: Int, private val subTypePrinter: Byter) : Byter {
    companion object {
        fun valueOf(long: Long) = Number.valueOf(long)
        fun valueOf(string: String) = Text.valueOf(string)
        fun valueAt(string: String) = Content.valueAt(string)
    }

    override val bytes: ByteArray by lazy { VarInt(subTypeId.toLong()).bytes + subTypePrinter.bytes }

    data class Number(val varInt: VarInt) : MultiThing(1, varInt) {
        companion object {
            fun valueOf(long: Long) = Number(VarInt.valueOf(long))
        }
    }

    data class Text(val varString: VarString) : MultiThing(2, varString) {
        companion object {
            fun valueOf(string: String) = Text(VarString.valueOf(string))
        }
    }

    data class Content(val contentId: ContentId) : MultiThing(3, contentId) {
        companion object {
            fun valueAt(string: String) = Content(ContentId.valueOf(string))
        }
    }
}