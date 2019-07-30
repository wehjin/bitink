package com.rubyhuntersky.rune.basics.byter

interface Debyter<out T : Any> {

    fun debyte(byteArray: ByteArray, start: Int = 0): T
}