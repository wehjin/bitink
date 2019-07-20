package com.rubyhuntersky.chain.block

import com.rubyhuntersky.chain.*

data class BlockHeader(
    val height: BlockHeight,
    val previousChainId: ChainId,
    val blockTime: BlockTime,
    val actionsHash: String,
    val nonce: Nonce,
    val historicalHash: String
) {
    val id: ChainId get() = TODO()

    fun isValid(previousChain: Chain): Boolean =
        height == (previousChain.height.inc())
                && previousChainId == previousChain.id
                && blockTime.isValid(previousChain)
}