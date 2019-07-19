package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.*


sealed class ActionData(val typeId: Int) {

    abstract val minHeight: BlockHeight

    data class SummonInk(
        override val minHeight: BlockHeight,
        val output: Ink
    ) : ActionData(typeId = 1)

    data class SiphonInk(
        override val minHeight: BlockHeight,
        val inputs: List<Nib>,
        val outputs: List<Ink>,
        val feeSize: Quantity
    ) : ActionData(typeId = 2)

    data class StateTag(
        override val minHeight: BlockHeight,
        val tag: Tag,
        val inputs: List<Nib>,
        val overflow: Ink,
        val feeSize: Quantity
    ) : ActionData(typeId = 3)

    data class SmashPen(
        override val minHeight: BlockHeight,
        val pen: PenName,
        val witnessLabel: WitnessLabel
    ) : ActionData(typeId = 4)
}

