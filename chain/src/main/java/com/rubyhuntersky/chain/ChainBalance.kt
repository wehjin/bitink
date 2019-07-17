package com.rubyhuntersky.chain

data class ChainBalance(
    val height: BlockHeight,
    val wetInk: Set<Ink>
) {

    fun applyAction(action: ChainAction): ChainBalance = action.applyToBalance(this)
}