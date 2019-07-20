package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.block.Block


class Recorder(startRecord: Record) {

    var record = startRecord

    fun addBlock(block: Block): AddBlockResult {
        val isValid = block.isValidForChain(record.longestChain)
        return AddBlockResult.Invalid
    }

    sealed class AddBlockResult {
        object Invalid : AddBlockResult()
    }
}