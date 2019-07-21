package com.rubyhuntersky.chain

data class DotData(
    val author: PenName,
    val entity: Entity,
    val attribute: Attribute,
    val value: Value,
    val asserted: Boolean
)