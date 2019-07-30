package com.rubyhuntersky.rune.basics.things

import com.rubyhuntersky.rune.basics.byter.Byter


data class VarString(val value: String) : Byter {

    companion object {
        fun valueOf(string: String) = VarString(string)
    }

    override val bytes: ByteArray by lazy {
        val header = VarInt.valueOf(value.length.toLong())
        val footer = value.toByteArray()
        header.bytes + footer
    }
}