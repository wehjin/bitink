package com.rubyhuntersky.chain


class Recorder(startRecord: Record) {

    var record = startRecord

    fun addBlock(block: Block): AddBlockResult {
        val isValid = block.isValid(record.longestChain)
        return AddBlockResult.Invalid
    }

    sealed class AddBlockResult {
        object Invalid : AddBlockResult()
    }
}