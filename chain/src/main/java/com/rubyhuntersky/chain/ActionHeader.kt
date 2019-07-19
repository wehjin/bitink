package com.rubyhuntersky.chain

data class ActionHeader(
    val id: ActionId,
    val minHeight: BlockHeight,
    val witnessLabel: WitnessLabel
)