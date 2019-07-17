package com.rubyhuntersky.chain

sealed class ChainAction {

    abstract val actionId: Id

    data class Id(
        val blockHeight: Long,
        val actionNumber: Long
    )

    data class RewardOperator(
        override val actionId: Id,
        val rewardWell: InkWell
    ) : ChainAction()

    data class TransferInk(
        override val actionId: Id,
        val startInks: List<Ink>,
        val endWells: List<InkWell>,
        val operatorReward: Long,
        val originatorSignature: Signature
    ) : ChainAction()

    data class AssertTag(
        override val actionId: Id,
        val author: PenName,
        val entity: Entity,
        val attribute: Attribute,
        val value: Value,
        val startInks: List<Ink>,
        val overflowWell: InkWell,
        val operatorReward: Long,
        val authorSignature: Signature
    ) : ChainAction()

    data class RetractTag(
        override val actionId: Id,
        val author: PenName,
        val entity: Entity,
        val attribute: Attribute,
        val startInks: List<Ink>,
        val overflowWell: InkWell,
        val operatorReward: Long,
        val authorSignature: Signature
    ) : ChainAction()
}

data class Ink(
    val wellActionId: ChainAction.Id,
    val wellIndex: Int
)

data class InkWell(
    val owner: PenName,
    val size: Long,
    val ink: Ink
) {
    val actionId get() = ink.wellActionId
    val index get() = ink.wellIndex
}

data class Signature(val string: String)