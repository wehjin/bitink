package com.rubyhuntersky.chain

data class Balance(
    val height: BlockHeight,
    val wetInk: Set<Ink>,
    val tallTags: Set<Tag>,
    val burntPens: Set<PenName>
) {

    fun applyAction(action: Action): Balance = action.applyToBalance(this)
}