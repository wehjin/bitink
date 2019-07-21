package com.rubyhuntersky.chain.witness

import com.rubyhuntersky.chain.basics.HashValue
import com.rubyhuntersky.chain.basics.PenName

data class Witness(
    val publicName: PenName,
    val messageHash: String,
    val signature: String
) {
    fun checkSignsHash(hashValue: HashValue) = check(false) { "Not implemented" }
}