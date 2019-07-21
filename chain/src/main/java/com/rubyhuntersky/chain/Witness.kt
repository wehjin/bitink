package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.block.HashValue

data class Witness(
    val publicName: PenName,
    val messageHash: String,
    val signature: String
) {
    fun checkSignsHash(hashValue: HashValue) = check(false) { "Not implemented" }
}