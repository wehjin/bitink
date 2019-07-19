package com.rubyhuntersky.chain

data class Balance(
    val height: BlockHeight,
    val wetInks: Set<Ink>,
    val assertedTags: Set<Tag>,
    val smashedPens: Set<PenName>
) {

    fun applyAction(action: Action): Balance = action.applyToBalance(this)
}