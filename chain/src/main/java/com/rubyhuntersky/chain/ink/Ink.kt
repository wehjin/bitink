package com.rubyhuntersky.chain.ink

import com.rubyhuntersky.chain.INK_DWELL_BLOCKS
import com.rubyhuntersky.chain.basics.Quantity
import com.rubyhuntersky.chain.block.Carry
import com.rubyhuntersky.chain.block.BlockHeight

data class Ink(
    val quantity: Quantity,
    val surface: Surface,
    val dryHeight: BlockHeight
) {
    fun checkOutput(carry: Carry) {
        check(quantity >= Quantity.ZERO)
        check(dryHeight == carry.blockHeight + INK_DWELL_BLOCKS)
        surface.checkOutput(carry)
    }
}

