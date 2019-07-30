package com.rubyhuntersky.rune

import com.rubyhuntersky.rune.basics.byter.Byter

data class Stone(
    val scriber: Rune.Creature,
    val entity: Rune,
    val attribute: Rune,
    val value: Rune
) : Byter {
    override val bytes: ByteArray by lazy {
        scriber.bytes + entity.bytes + attribute.bytes + value.bytes
    }
}

