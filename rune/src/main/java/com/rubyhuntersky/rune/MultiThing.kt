package com.rubyhuntersky.rune

sealed class MultiThing(private val subTypeId: Int, private val subTypePrinter: Byter) :
    Byter {

    override val bytes: ByteArray by lazy { VarInt(subTypeId.toLong()).bytes + subTypePrinter.bytes }

    companion object {
        fun valueOf(long: Long) = Number.valueOf(long)
        fun valueOf(string: String) = Text.valueOf(string)
    }

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
}