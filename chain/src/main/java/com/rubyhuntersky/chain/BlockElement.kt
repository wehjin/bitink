package com.rubyhuntersky.chain

sealed class BlockElement {

    data class Header(
        val height: BlockHeight,
        val previousChainId: ChainId,
        val blockTime: BlockTime,
        val bodyHash: String,
        val nonce: Nonce,
        val historicalHash: String
    ) : BlockElement() {
        val id: ChainId get() = TODO()

        fun isValid(previousChain: Chain): Boolean =
            height == (previousChain.height.inc())
                    && previousChainId == previousChain.id
                    && blockTime.isValid(previousChain)
    }

    data class Body(
        val actions: List<Action>
    ) : BlockElement() {
        val hash: String get() = TODO()

        fun isValid(previousChain: Chain, header: Header): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun applyActions(balance: Balance): Balance = actions.fold(balance, Balance::applyAction)
    }
}