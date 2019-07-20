package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.block.BlockHeight

sealed class Surface(val typeId: Int) {

    object Thick : Surface(1)

    object Thin : Surface(2)

    data class WitnessRequired(val pen: PenName) : Surface(3)

    data class KeyRequired(
        val releaseKeyHash: String,
        val releasePen: PenName,
        val returnPen: PenName,
        val returnAfter: BlockHeight
    ) : Surface(4)
}
