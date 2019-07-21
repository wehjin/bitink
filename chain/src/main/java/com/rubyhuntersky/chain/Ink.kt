package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.block.ChainState
import com.rubyhuntersky.chain.block.BlockHeight

data class Ink(
    val quantity: Quantity,
    val surface: Surface,
    val dryHeight: BlockHeight
) {
    fun checkOutput(chainState: ChainState) {
        check(quantity >= Quantity.ZERO)
        check(dryHeight == chainState.height + INK_DWELL_BLOCKS)
        surface.checkOutput(chainState)
    }
}

