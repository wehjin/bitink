package com.rubyhuntersky.chain.basics

fun Byte.toIntUnsigned(): Int = (this.toInt() + 256)
fun Int.toSignedByte(): Byte = (this - 256).toByte()