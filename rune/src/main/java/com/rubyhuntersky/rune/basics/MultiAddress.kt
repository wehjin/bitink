package com.rubyhuntersky.rune.basics

import com.rubyhuntersky.rune.basics.byter.Byter

data class MultiAddress(val value: String) : Byter {
    companion object {
        fun valueOf(string: String) = MultiAddress(string)
    }

    override val bytes: ByteArray get() = TODO()
}
