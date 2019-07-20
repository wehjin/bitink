package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.*
import com.rubyhuntersky.chain.block.BlockHeight


sealed class ActionData(val typeId: Int) {

    abstract val minHeight: BlockHeight

    data class AddInk(
        override val minHeight: BlockHeight,
        val output: Ink
    ) : ActionData(typeId = 1)

    data class MoveInk(
        override val minHeight: BlockHeight,
        val inputs: List<Dip>,
        val outputs: List<Ink>,
        val feeSize: Quantity
    ) : ActionData(typeId = 2)

    data class WriteDot(
        override val minHeight: BlockHeight,
        val dot: Dot,
        val inputs: List<Dip>,
        val overflow: Ink,
        val feeSize: Quantity
    ) : ActionData(typeId = 3)

    data class SmashPen(
        override val minHeight: BlockHeight,
        val pen: PenName,
        val witnessLabel: WitnessLabel
    ) : ActionData(typeId = 4)
}

