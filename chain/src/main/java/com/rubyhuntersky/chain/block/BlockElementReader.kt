package com.rubyhuntersky.chain.block

interface BlockElementReader<out T : BlockElement> {

    fun read(byteArray: ByteArray, start: Long): T
}