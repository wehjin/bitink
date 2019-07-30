package com.rubyhuntersky.rune.basics.things

import com.rubyhuntersky.rune.basics.byter.Byter

data class ByteBoolean(val value: Boolean) : Byter {
    companion object {
        fun valueOf(boolean: Boolean) = ByteBoolean(boolean)
    }

    override val bytes: ByteArray by lazy { byteArrayOf(if (value) 1 else 0) }
}