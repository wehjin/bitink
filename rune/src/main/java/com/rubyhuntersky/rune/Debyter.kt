package com.rubyhuntersky.rune

interface Debyter<out T : Any> {

    fun debyte(byteArray: ByteArray, start: Int = 0): T
}