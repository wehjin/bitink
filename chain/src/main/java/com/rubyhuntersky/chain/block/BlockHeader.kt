package com.rubyhuntersky.chain.block

import com.rubyhuntersky.chain.basics.HashValue

data class BlockHeader(
    val height: BlockHeight,
    val previousBlockHash: HashValue,
    val blockTime: BlockTime,
    val actionListHash: HashValue,
    val nonce: Nonce,
    val historicalHash: String
) {
    val hash: HashValue get() = TODO()

}