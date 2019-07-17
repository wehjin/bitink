package com.rubyhuntersky.chain

data class InkBall(
    val id: InkBallId,
    val size: Long,
    val owner: PenName
) {
    val useBeforeHeight: BlockHeight get() = id.actionId.blockHeight + INK_DWELL_LENGTH
}