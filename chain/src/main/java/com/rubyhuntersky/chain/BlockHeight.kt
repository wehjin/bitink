package com.rubyhuntersky.chain

data class BlockHeight(val value: Long) {
    operator fun inc(): BlockHeight = copy(value = value + 1)
    operator fun plus(distance: Long): BlockHeight = copy(value = value + distance)
}