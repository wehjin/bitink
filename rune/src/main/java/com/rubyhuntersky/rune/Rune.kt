package com.rubyhuntersky.rune

sealed class Rune(private val subTypeId: Int, private val subTypePrinter: Byter) : Byter {

    override val bytes: ByteArray by lazy { VarInt(subTypeId.toLong()).bytes + subTypePrinter.bytes }

    data class Creature(val multiName: MultiName) : Rune(1, multiName) {
        companion object {
            fun valueOf(nameString: String) = Creature(MultiName.valueOf(nameString))
        }
    }

    data class Place(val multiAddress: MultiAddress) : Rune(2, multiAddress) {
        companion object {
            fun valueAt(addressString: String) = Place(MultiAddress.valueOf(addressString))
        }
    }

    data class Thing(val multiThing: MultiThing) : Rune(3, multiThing) {
        companion object {
            fun valueOf(long: Long) = Thing(MultiThing.valueOf(long))
            fun valueOf(textString: String) = Thing(MultiThing.valueOf(textString))
            fun valueAt(contentIdString: String) = Thing(MultiThing.valueAt(contentIdString))
        }
    }
}
