package com.rubyhuntersky.chain.block

import com.rubyhuntersky.chain.action.ActionData
import com.rubyhuntersky.chain.basics.HashValue

data class ActionDataList(
    val actions: List<ActionData>
) {
    val hashValue: HashValue by lazy {
        val todo = actions.fold("", { hash, action -> "$hash:$action" })
        HashValue(todo)
    }
}