package com.rubyhuntersky.chain.block

data class BlockHeight(val value: Long) {
    operator fun inc(): BlockHeight = copy(value = value + 1)
    operator fun plus(distance: Long): BlockHeight = copy(value = value + distance)
    operator fun compareTo(other: BlockHeight) = value.compareTo(other.value)

    val isValid: Boolean
        get() = TODO()
}