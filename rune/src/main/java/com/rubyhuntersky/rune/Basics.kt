package com.rubyhuntersky.rune

fun Byte.toIntUnsigned(): Int = toInt() and 0xff
fun Int.toSignedByte(): Byte =
    if (this and 0x80 == 0x80) ((-1 and 0x80) or (this and 0x7f)).toByte()
    else this.toByte()

fun IntArray.toSignedByteArray(): ByteArray = this.map(Int::toSignedByte).toByteArray()
fun List<Int>.toSignedByteArray(): ByteArray = this.map(Int::toSignedByte).toByteArray()

fun Byte.toHex(): String = "0x${Integer.toHexString(toIntUnsigned())}"
fun ByteArray.toHex(): List<String> = this.map(Byte::toHex)