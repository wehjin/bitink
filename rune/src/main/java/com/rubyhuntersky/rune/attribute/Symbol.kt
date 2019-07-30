package com.rubyhuntersky.rune.attribute

import com.rubyhuntersky.rune.basics.byter.Byter
import com.rubyhuntersky.rune.basics.things.VarString

data class Symbol(val symbol: VarString) : Byter {
    init {
        require(symbol.value == symbol.value.trim().toLowerCase())
    }

    override val bytes: ByteArray by lazy { symbol.bytes }
}