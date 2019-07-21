package com.rubyhuntersky.chain.dot

sealed class Value {
    data class INT(val int: Int) : Value()
}