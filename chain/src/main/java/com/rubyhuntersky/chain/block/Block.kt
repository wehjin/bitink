package com.rubyhuntersky.chain.block

import com.rubyhuntersky.chain.witness.WitnessMap


data class Block(
    val header: BlockHeader,
    val actionDataList: ActionDataList,
    val witnessMap: WitnessMap
)
