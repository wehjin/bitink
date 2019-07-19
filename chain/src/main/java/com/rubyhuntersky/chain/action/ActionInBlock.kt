package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.Balance

data class ActionInBlock(
    val id: ActionId,
    val action: ActionData
) {
    fun applyToBalance(balance: Balance): Balance {
        TODO("not implemented")
    }

    fun isValid(): Boolean = TODO()
}