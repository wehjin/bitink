package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.action.BlockAction
import com.rubyhuntersky.chain.block.BlockHeight

data class Balance(
    val height: BlockHeight,
    val wetInks: Set<Ink>,
    val assertedDots: Set<Dot>,
    val smashedPens: Set<PenName>
) {

    fun applyAction(action: BlockAction): Balance = action.applyToBalance(this)
}