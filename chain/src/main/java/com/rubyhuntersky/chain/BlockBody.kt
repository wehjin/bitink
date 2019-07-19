package com.rubyhuntersky.chain

data class BlockBody(
    val actions: List<Action>
) {
    val hash: String get() = TODO()

    fun isValid(previousChain: Chain, header: BlockHeader): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun applyActions(balance: Balance): Balance = actions.fold(balance, Balance::applyAction)
}
