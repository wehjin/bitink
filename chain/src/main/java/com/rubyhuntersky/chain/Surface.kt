package com.rubyhuntersky.chain

sealed class Surface {

    object Thick : Surface()

    object Thin : Surface()

    data class Sign(val pen: PenName) : Surface()

    data class ReleaseOrReturn(
        val releaseKeyHash: String,
        val releasePen: PenName,
        val returnPen: PenName,
        val returnAfter: BlockHeight
    )
}
