package com.rubyhuntersky.chain

data class Ink(
    val id: InkId,
    val size: Long,
    val owner: PenName
) {
    val useBeforeHeight: BlockHeight get() = id.actionId.blockHeight + INK_DWELL_LENGTH
}