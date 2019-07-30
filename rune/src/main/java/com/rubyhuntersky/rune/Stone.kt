package com.rubyhuntersky.rune

import com.rubyhuntersky.rune.attribute.Attribute
import com.rubyhuntersky.rune.basics.byter.Byter
import com.rubyhuntersky.rune.rune.Rune

data class Stone(
    val scriber: Rune.Spirit,
    val entity: Rune,
    val attribute: Attribute,
    val value: Rune
) : Byter {
    override val bytes: ByteArray by lazy {
        scriber.bytes + entity.bytes + attribute.bytes + value.bytes
    }
}

