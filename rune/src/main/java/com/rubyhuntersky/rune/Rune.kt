package com.rubyhuntersky.rune

import com.rubyhuntersky.rune.basics.MultiAddress
import com.rubyhuntersky.rune.basics.MultiName
import com.rubyhuntersky.rune.basics.MultiThing
import com.rubyhuntersky.rune.basics.byter.Byter
import com.rubyhuntersky.rune.basics.things.VarInt

sealed class Rune(private val subTypeId: Int, private val subTypePrinter: Byter) :
    Byter {

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
