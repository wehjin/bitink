package com.rubyhuntersky.chain

sealed class Nib {

    abstract val inkId: InkId

    data class Plain(
        override val inkId: InkId
    ) : Nib()

    data class Signer(
        override val inkId: InkId,
        val witnessLabel: WitnessLabel?
    ) : Nib()

    data class ReleaseKey(
        override val inkId: InkId,
        val releaseKey: String,
        val witnessLabel: WitnessLabel?
    ) : Nib()
}