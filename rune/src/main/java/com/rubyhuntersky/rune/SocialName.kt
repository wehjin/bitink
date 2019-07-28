package com.rubyhuntersky.rune

data class SocialName(val name: String) : Byter {
    override val bytes: ByteArray
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    companion object {

        fun of(bech32: String): SocialName = SocialName(bech32)
    }
}