package com.rubyhuntersky.rune

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
