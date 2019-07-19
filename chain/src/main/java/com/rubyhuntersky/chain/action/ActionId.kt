package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.BlockHash

data class ActionId(
    val blockHash: BlockHash,
    val actionIndex: Long
)