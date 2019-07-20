package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.block.BlockHeight

data class Ink(
    val id: InkId,
    val quantity: Quantity,
    val surface: Surface,
    val dryHeight: BlockHeight
)

