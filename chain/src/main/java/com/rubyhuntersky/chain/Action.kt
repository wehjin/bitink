package com.rubyhuntersky.chain

sealed class Action {

    abstract val id: ActionId
    abstract val signature: Signature

    fun applyToBalance(balance: Balance): Balance {
        TODO("not implemented")
    }

    data class Summon(
        override val id: ActionId,
        val output: Ink,
        override val signature: Signature
    ) : Action()

    data class Send(
        override val id: ActionId,
        val inputs: List<InkId>,
        val outputs: List<Ink>,
        val feeSize: Long,
        override val signature: Signature
    ) : Action()

    data class State(
        override val id: ActionId,
        val tag: Tag,
        val inputs: List<InkId>,
        val overflow: Ink,
        val feeSize: Long,
        override val signature: Signature
    ) : Action()

    data class Burn(
        override val id: ActionId,
        val penName: PenName,
        override val signature: Signature
    ) : Action()
}

