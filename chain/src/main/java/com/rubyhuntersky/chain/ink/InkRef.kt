package com.rubyhuntersky.chain.ink

import com.rubyhuntersky.chain.action.ActionRef

data class InkRef(
    val actionRef: ActionRef,
    val inkIndex: Int
)