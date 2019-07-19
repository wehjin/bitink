package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.action.ActionInBlock

data class Block(
    val header: BlockHeader,
    val actions: List<ActionInBlock>,
    val witnessMap: Map<WitnessLabel, Witness>
) {
    fun isValidForChain(previousChain: Chain): Boolean {
        // TODO witnesses are valid
        // TODO witnesses confirm actions
        return (header.actionsHash == actions.hash
                && header.isValid(previousChain)
                && actions.isValidForChain(previousChain))
    }

    private val List<ActionInBlock>.hash: String
        get() = this.fold("", { hash, action -> "$hash:$action" })

    private fun List<ActionInBlock>.isValidForChain(previousChain: Chain): Boolean =
        this.fold(true, { result, action -> result || action.isValid() })

    fun applyActions(balance: Balance): Balance = actions.fold(balance, Balance::applyAction)
}