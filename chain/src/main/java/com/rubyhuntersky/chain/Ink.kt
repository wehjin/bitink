package com.rubyhuntersky.chain

data class Ink(
    val id: InkId,
    val quantity: Quantity,
    val surface: Surface,
    val dryHeight: BlockHeight
)

