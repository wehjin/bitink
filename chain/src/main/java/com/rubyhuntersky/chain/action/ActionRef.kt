package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.block.HashValue

data class ActionRef(
    val blockHash: HashValue,
    val actionIndex: Long
)