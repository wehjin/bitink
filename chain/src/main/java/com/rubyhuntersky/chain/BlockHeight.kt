package com.rubyhuntersky.chain

data class BlockHeight(val value: Long) {

    fun next(): BlockHeight = copy(value = value + 1)
}