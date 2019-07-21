package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.witness.Witness
import com.rubyhuntersky.chain.witness.WitnessLabel

data class PoolAction(
    val action: ActionData,
    val witnesses: Map<WitnessLabel, Witness>
)