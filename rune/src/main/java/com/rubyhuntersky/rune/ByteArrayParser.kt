package com.rubyhuntersky.rune

interface ByteArrayParser<out T : Any> {

    fun read(byteArray: ByteArray, start: Int = 0): T
}