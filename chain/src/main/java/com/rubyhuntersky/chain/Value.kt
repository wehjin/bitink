package com.rubyhuntersky.chain

sealed class Value {
    data class INT(val int: Int) : Value()
}