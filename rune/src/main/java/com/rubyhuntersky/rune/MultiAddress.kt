package com.rubyhuntersky.rune

data class MultiAddress(val value: String) : Byter {
    companion object {
        fun valueOf(string: String) = MultiAddress(string)
    }

    override val bytes: ByteArray get() = TODO()
}
