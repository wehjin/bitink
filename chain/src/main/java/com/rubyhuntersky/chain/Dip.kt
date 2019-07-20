package com.rubyhuntersky.chain

sealed class Dip(val typeId: Int) {
    abstract val inkId: InkId

    data class PEN(
        override val inkId: InkId,
        val witnessLabel: WitnessLabel
    ) : Dip(2)

    data class KEY_AND_PEN(
        override val inkId: InkId,
        val releaseKey: String,
        val witnessLabel: WitnessLabel
    ) : Dip(3)
}