package com.rubyhuntersky.chain.basics

import java.math.BigInteger

data class Quantity(val amount: BigInteger) {

    operator fun compareTo(other: Quantity) = amount.compareTo(other.amount)
    operator fun plus(other: Quantity) =
        Quantity(amount + other.amount)
    operator fun unaryMinus() = Quantity(-amount)

    companion object {
        val ZERO = Quantity(BigInteger.ZERO)
        val ONE = Quantity(BigInteger.ONE)
    }
}