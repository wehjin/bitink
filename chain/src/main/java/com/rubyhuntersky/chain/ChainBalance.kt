package com.rubyhuntersky.chain

data class ChainBalance(
    val height: BlockHeight,
    val wetInk: Set<InkBallId>
) {

    fun applyAction(action: ChainAction): ChainBalance = action.applyToBalance(this)
}