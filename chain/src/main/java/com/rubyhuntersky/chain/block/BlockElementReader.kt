package com.rubyhuntersky.chain.block

interface BlockElementReader<out T : BlockElement> {

    fun read(byteArray: ByteArray, start: Int = 0): T
}