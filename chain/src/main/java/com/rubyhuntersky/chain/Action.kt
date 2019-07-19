package com.rubyhuntersky.chain

sealed class Action(val typeNumber: Int) {
    abstract val header: ActionHeader

    val id get() = header.id
    val minHeight get() = header.minHeight
    val witnessId get() = header.witnessId

    fun applyToBalance(balance: Balance): Balance {
        TODO("not implemented")
    }

    data class SimmerInk(
        override val header: ActionHeader,
        val output: Ink
    ) : Action(typeNumber = 1)


    data class SiphonInk(
        override val header: ActionHeader,
        val inputs: List<Nib>,
        val outputs: List<Ink>,
        val feeSize: Long
    ) : Action(typeNumber = 2)

    data class StateTag(
        override val header: ActionHeader,
        val tag: Tag,
        val inputs: List<Nib>,
        val overflow: Ink,
        val feeSize: Long
    ) : Action(typeNumber = 3)

    data class SmashPen(
        override val header: ActionHeader,
        val burnPen: PenName
    ) : Action(typeNumber = 4)
}

