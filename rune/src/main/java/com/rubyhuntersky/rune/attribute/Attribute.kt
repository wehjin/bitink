package com.rubyhuntersky.rune.attribute

import com.rubyhuntersky.rune.basics.byter.Byter

data class Attribute(
    val groupSymbol: Symbol,
    val elementSymbol: Symbol,
    val cardinality: Cardinality
) : Byter {

    override val bytes: ByteArray by lazy {
        groupSymbol.bytes + elementSymbol.bytes + cardinality.bytes
    }
}