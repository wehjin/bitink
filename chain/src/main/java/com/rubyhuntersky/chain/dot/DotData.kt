package com.rubyhuntersky.chain.dot

import com.rubyhuntersky.chain.basics.PenName
import com.rubyhuntersky.rune.attribute.Attribute

data class DotData(
    val author: PenName,
    val entity: Entity,
    val attribute: Attribute,
    val value: Value,
    val asserted: Boolean
)