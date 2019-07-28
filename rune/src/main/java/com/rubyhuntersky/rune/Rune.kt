package com.rubyhuntersky.rune

sealed class Rune : ByteArrayPrinter {

    data class Number(
        val varInt: VarInt
    )

}
