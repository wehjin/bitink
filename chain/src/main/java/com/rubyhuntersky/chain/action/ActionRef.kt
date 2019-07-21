package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.basics.HashValue

data class ActionRef(
    val blockHash: HashValue,
    val actionIndex: Long
)