package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.action.ActionInBlock

data class Balance(
    val height: BlockHeight,
    val wetInks: Set<Ink>,
    val assertedDots: Set<Dot>,
    val smashedPens: Set<PenName>
) {

    fun applyAction(action: ActionInBlock): Balance = action.applyToBalance(this)
}