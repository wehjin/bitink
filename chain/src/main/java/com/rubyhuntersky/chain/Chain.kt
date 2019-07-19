package com.rubyhuntersky.chain

sealed class Chain {

    abstract val id: ChainId
    abstract val balance: Balance

    val height: BlockHeight
        get() = balance.height

    object Genesis : Chain() {
        override val id: ChainId get() = TODO("not implemented")
        override val balance: Balance
            get() = Balance(
                height = BlockHeight(0),
                wetInks = emptySet(),
                assertedTags = emptySet(),
                smashedPens = emptySet()
            )
    }

    data class Modern(
        val body: Chain,
        val tail: Block
    ) : Chain() {
        override val id: ChainId get() = tail.header.id
        override val balance: Balance get() = tail.applyActions(body.balance)
    }
}