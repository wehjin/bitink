package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.Witness
import com.rubyhuntersky.chain.WitnessLabel

data class ActionInPool(
    val action: ActionData,
    val witnesses: Map<WitnessLabel, Witness>
)