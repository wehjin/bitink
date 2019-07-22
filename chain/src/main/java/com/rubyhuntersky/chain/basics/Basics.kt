package com.rubyhuntersky.chain.basics

fun Byte.toIntUnsigned(): Int = (this.toInt() + 256)
fun Int.toSignedByte(): Byte = (this - 256).toByte()
fun IntArray.toSignedByteArray(): ByteArray = this.map(Int::toSignedByte).toByteArray()
fun List<Int>.toSignedByteArray(): ByteArray = this.map(Int::toSignedByte).toByteArray()

fun Byte.toHex(): String = "$0x${Integer.toHexString(this.toIntUnsigned())}"
fun ByteArray.toHex(): List<String> = this.map(Byte::toHex)