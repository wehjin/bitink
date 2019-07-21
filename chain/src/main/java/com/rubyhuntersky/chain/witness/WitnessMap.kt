package com.rubyhuntersky.chain.witness

data class WitnessMap(
    val map: Map<WitnessLabel, Witness>
) {
    fun findWitness(label: WitnessLabel): Witness = TODO()
}