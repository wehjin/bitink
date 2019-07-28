package com.rubyhuntersky.rune

sealed class Rune(private val subTypeId: Int, private val subTypePrinter: Byter) : Byter {

    override val bytes: ByteArray by lazy { VarInt(subTypeId.toLong()).bytes + subTypePrinter.bytes }

    data class Creature(val multiName: MultiName) : Rune(1, multiName) {
        companion object {
            fun valueOf(string: String) = Creature(MultiName(string))
        }
    }

    data class Place(val multiAddress: MultiAddress) : Rune(2, multiAddress) {
        companion object {
            fun valueOf(string: String) = Place(MultiAddress.valueOf(string))
        }
    }

    data class Thing(val multiThing: MultiThing) : Rune(3, multiThing) {
        companion object {
            fun valueOf(long: Long) = Thing(MultiThing.valueOf(long))
            fun valueOf(string: String) = Thing(MultiThing.valueOf(string))
        }
    }
}
