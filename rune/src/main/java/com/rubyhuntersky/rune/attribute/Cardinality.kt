package com.rubyhuntersky.rune.attribute

import com.rubyhuntersky.rune.basics.byter.Byter
import com.rubyhuntersky.rune.basics.things.VarInt

sealed class Cardinality(private val typeId: Long) : Byter {

    override val bytes: ByteArray by lazy {
        VarInt(typeId).bytes
    }

    object One : Cardinality(1)
    object ManyDistinct : Cardinality(2)
}