package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.block.Block
import com.rubyhuntersky.chain.block.BlockHeight
import com.rubyhuntersky.chain.block.ChainState
import com.rubyhuntersky.chain.block.HashValue

sealed class Chain {

    abstract val tallBlockHash: HashValue
    abstract val chainState: ChainState

    val height: BlockHeight
        get() = chainState.height

    object Genesis : Chain() {
        override val tallBlockHash: HashValue get() = TODO("not implemented")
        override val chainState: ChainState
            get() = ChainState(
                height = BlockHeight(0),
                wetInks = emptySet(),
                assertedDots = emptySet(),
                smashedPens = emptySet()
            )
    }

    data class Taller(
        val tallBlock: Block,
        val shortChain: Chain,
        override val chainState: ChainState
    ) : Chain() {
        override val tallBlockHash: HashValue get() = tallBlock.header.hash
    }
}