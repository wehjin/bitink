package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.basics.HashValue
import com.rubyhuntersky.chain.block.Block
import com.rubyhuntersky.chain.block.BlockHeight
import com.rubyhuntersky.chain.block.BlockTime
import com.rubyhuntersky.chain.block.Carry

sealed class Chain {

    abstract val tallBlockHash: HashValue
    abstract val carry: Carry

    val height: BlockHeight
        get() = carry.blockHeight

    private fun isValidBlockTime(blockTime: BlockTime): Boolean = TODO()

    fun step(block: Block): Chain {
        require(block.header.previousBlockHash == tallBlockHash)
        require(isValidBlockTime(block.header.blockTime))
        require(block.header.actionListHash == block.actionDataList.hashValue)
        val initial = carry.incrementHeight()
        val final =
            block.actionDataList.actions.fold(initial, { state, action -> action.step(state, block.witnessMap) })
        return Taller(block, this, final)
    }

    object Genesis : Chain() {
        override val tallBlockHash: HashValue get() = TODO("not implemented")
        override val carry: Carry
            get() = Carry(
                blockHeight = BlockHeight(0),
                wetInks = emptySet(),
                assertedDots = emptySet(),
                smashedPens = emptySet()
            )
    }

    data class Taller(
        val tallBlock: Block,
        val shortChain: Chain,
        override val carry: Carry
    ) : Chain() {
        override val tallBlockHash: HashValue get() = tallBlock.header.hash
    }
}