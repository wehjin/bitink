package com.rubyhuntersky.chain

data class Block(
    val header: BlockHeader,
    val body: BlockBody
) {
    fun isValid(previousChain: Chain): Boolean =
        header.bodyHash == body.hash && header.isValid(previousChain) && body.isValid(previousChain, header)

    fun applyActions(balance: Balance): Balance = body.applyActions(balance)
}