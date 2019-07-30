package com.rubyhuntersky.rune.basics.things

import com.rubyhuntersky.rune.basics.byter.Byter

data class MultiName(val name: String) : Byter {
    override val bytes: ByteArray
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    companion object {

        fun valueOf(bech32: String): MultiName =
            MultiName(bech32)
    }
}