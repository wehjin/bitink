package com.rubyhuntersky.chain.block

import com.rubyhuntersky.chain.Balance
import com.rubyhuntersky.chain.Chain
import com.rubyhuntersky.chain.Witness
import com.rubyhuntersky.chain.WitnessLabel
import com.rubyhuntersky.chain.action.BlockAction

data class Block(
    val header: BlockHeader,
    val actions: List<BlockAction>,
    val witnessMap: Map<WitnessLabel, Witness>
) {
    fun isValidForChain(previousChain: Chain): Boolean {
        // TODO witnesses are valid
        // TODO witnesses confirm actions
        return (header.actionsHash == actions.hash
                && header.isValid(previousChain)
                && actions.isValidForChain(previousChain))
    }

    private val List<BlockAction>.hash: String
        get() = this.fold("", { hash, action -> "$hash:$action" })

    private fun List<BlockAction>.isValidForChain(previousChain: Chain): Boolean =
        this.fold(true, { result, action -> result || action.isValid() })

    fun applyActions(balance: Balance): Balance = actions.fold(balance, Balance::applyAction)
}