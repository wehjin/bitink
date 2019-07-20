package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.action.ActionId

data class Dot(
    val author: PenName,
    val entity: Entity,
    val attribute: Attribute,
    val value: Value,
    val asserted: Boolean,
    val actionId: ActionId
)