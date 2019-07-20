package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.block.BlockHash

data class ActionId(
    val blockHash: BlockHash,
    val actionIndex: Long
)