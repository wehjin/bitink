package com.rubyhuntersky.rune.rune

import com.rubyhuntersky.rune.basics.things.MultiName
import com.rubyhuntersky.rune.basics.byter.Byter
import com.rubyhuntersky.rune.basics.things.ByteBoolean
import com.rubyhuntersky.rune.basics.things.ContentId
import com.rubyhuntersky.rune.basics.things.VarInt
import com.rubyhuntersky.rune.basics.things.VarString

sealed class Rune(private val typeId: Long, private val typeByter: Byter) :
    Byter {

    override val bytes: ByteArray by lazy {
        VarInt(typeId).bytes + typeByter.bytes
    }

    data class Truth(val value: ByteBoolean) : Rune(1, value) {
        companion object {
            fun valueOf(truthValue: Boolean) = Truth(ByteBoolean.valueOf(truthValue))
        }
    }

    data class Number(val value: VarInt) : Rune(2, value) {
        companion object {
            fun valueOf(numberValue: Long) = Number(VarInt.valueOf(numberValue))
        }
    }

    data class Text(val value: VarString) : Rune(3, value) {
        companion object {
            fun valueOf(textValue: String) = Text(VarString.valueOf(textValue))
        }
    }

    data class Content(val value: ContentId) : Rune(4, value) {
        companion object {
            fun valueAt(cidValue: String) = Content(ContentId.valueOf(cidValue))
        }
    }

    data class Spirit(val multiName: MultiName) : Rune(5, multiName) {
        companion object {
            fun valueOf(nameValue: String) = Spirit(MultiName.valueOf(nameValue))
        }
    }
}
