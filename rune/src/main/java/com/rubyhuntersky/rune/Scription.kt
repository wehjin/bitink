package com.rubyhuntersky.rune

data class Scription<out T : Byter>(
    val subject: T,
    val scribed: Boolean
) : Byter {
    override val bytes: ByteArray by lazy {
        val scribedByte = (if (scribed) 1 else 0).toByte()
        subject.bytes + scribedByte
    }
}