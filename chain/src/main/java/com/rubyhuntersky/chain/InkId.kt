package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.action.ActionId

data class InkId(
    val actionId: ActionId,
    val inkIndex: Int
)