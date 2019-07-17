package com.rubyhuntersky.chain

sealed class ChainAction {

    abstract val id: ChainActionId
    abstract val signature: Signature

    fun applyToBalance(balance: ChainBalance): ChainBalance {
        TODO("not implemented")
    }

    data class SummonInk(
        override val id: ChainActionId,
        val output: InkBall,
        override val signature: Signature
    ) : ChainAction()

    data class TransferInk(
        override val id: ChainActionId,
        val inputs: List<InkBallId>,
        val outputs: List<InkBall>,
        val feeSize: Long,
        override val signature: Signature
    ) : ChainAction()

    data class AssertTag(
        override val id: ChainActionId,
        val tagId: TagId,
        val tagValue: Value,
        val inputs: List<InkBallId>,
        val overflow: InkBall,
        val feeSize: Long,
        override val signature: Signature
    ) : ChainAction()

    data class RetractTag(
        override val id: ChainActionId,
        val tagId: TagId,
        val inputs: List<InkBallId>,
        val overflow: InkBall,
        val feeSize: Long,
        override val signature: Signature
    ) : ChainAction()

    data class BurnPen(
        override val id: ChainActionId,
        val penName: PenName,
        override val signature: Signature
    ) : ChainAction()
}

