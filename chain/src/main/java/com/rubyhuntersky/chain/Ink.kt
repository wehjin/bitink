package com.rubyhuntersky.chain

data class Ink(
    val inkId: InkId,
    val size: Long,
    val surface: Surface
) {
    val useBeforeHeight: BlockHeight get() = inkId.actionId.blockHeight + INK_DWELL_LENGTH
}

