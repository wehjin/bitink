package com.rubyhuntersky.chain

sealed class Nib(val typeId: Int) {
    abstract val inkId: InkId

    data class Plain(
        override val inkId: InkId
    ) : Nib(1)

    data class WitnessProvider(
        override val inkId: InkId,
        val witnessLabel: WitnessLabel
    ) : Nib(2)

    data class KeyProvider(
        override val inkId: InkId,
        val releaseKey: String,
        val witnessLabel: WitnessLabel
    ) : Nib(3)
}