package com.rubyhuntersky.chain

data class TagId(
    val author: PenName,
    val entity: Entity,
    val attribute: Attribute
)

sealed class ChainAction {

    abstract val actionId: ActionId

    fun applyToBalance(balance: ChainBalance): ChainBalance {
        TODO("not implemented")
    }

    data class RewardOperator(
        override val actionId: ActionId,
        val rewardWell: InkWell
    ) : ChainAction()

    data class TransferInk(
        override val actionId: ActionId,
        val startInks: List<Ink>,
        val endWells: List<InkWell>,
        val operatorReward: Long,
        val originatorSignature: Signature
    ) : ChainAction()

    data class AssertTag(
        override val actionId: ActionId,
        val tagId: TagId,
        val tagValue: Value,
        val startInks: List<Ink>,
        val overflowWell: InkWell,
        val operatorReward: Long,
        val authorSignature: Signature
    ) : ChainAction()

    data class RetractTag(
        override val actionId: ActionId,
        val tagId: TagId,
        val startInks: List<Ink>,
        val overflowWell: InkWell,
        val operatorReward: Long,
        val authorSignature: Signature
    ) : ChainAction()
}

data class Ink(
    val wellActionId: ActionId,
    val wellIndex: Int
)

data class InkWell(
    val size: Long,
    val ink: Ink,
    val owner: PenName
) {
    val actionId get() = ink.wellActionId
    val index get() = ink.wellIndex
}

data class Signature(val string: String)