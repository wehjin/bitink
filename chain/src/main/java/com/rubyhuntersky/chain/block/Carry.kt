package com.rubyhuntersky.chain.block

import com.rubyhuntersky.chain.basics.PenName
import com.rubyhuntersky.chain.basics.Quantity
import com.rubyhuntersky.chain.dot.DotData
import com.rubyhuntersky.chain.ink.Ink
import com.rubyhuntersky.chain.ink.InkRef

data class Carry(
    val blockHeight: BlockHeight,
    val wetInks: Set<Ink>,
    val assertedDots: Set<DotData>,
    val smashedPens: Set<PenName>
) {
    val rewardQuantity: Quantity
        get() = TODO()

    val dotCost: Quantity
        get() = -Quantity.ZERO

    val burnCost: Quantity
        get() = -Quantity.ONE

    fun incrementHeight(): Carry {
        val newHeight = blockHeight.inc()
        TODO("Remove dry inks, faded dots")
        return copy(blockHeight = newHeight)
    }

    fun findWetInk(inkRef: InkRef): Ink = TODO()

    fun moveInk(to: List<Ink>, from: List<Ink>): Carry = TODO()

    fun assertDot(dotData: DotData): Carry = TODO()

    fun burnPen(penName: PenName): Carry = TODO()
}