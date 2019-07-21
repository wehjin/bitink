package com.rubyhuntersky.chain.block

import com.rubyhuntersky.chain.*
import com.rubyhuntersky.chain.action.ActionData

data class ChainState(
    val height: BlockHeight,
    val wetInks: Set<Ink>,
    val assertedDots: Set<DotData>,
    val smashedPens: Set<PenName>
) {
    val rewardQuantity: Quantity
        get() = TODO()

    val dotCost: Quantity
        get() = -Quantity.ZERO

    val burnCost: Quantity
        get() = -Quantity.ONE

    fun incrementHeight(): ChainState {
        val newHeight = height.inc()
        TODO("Remove dry inks, faded dots")
        return copy(height = newHeight)
    }

    fun addInk(vararg ink: Ink): ChainState = TODO()

    fun findWetInk(inkRef: InkRef): Ink = TODO()

    fun moveInk(to: List<Ink>, from: List<Ink>): ChainState = TODO()

    fun assertDot(dotData: DotData): ChainState = TODO()

    fun burnPen(penName: PenName): ChainState = TODO()
}


data class Block(
    val header: BlockHeader,
    val actionDataList: ActionDataList,
    val witnessMap: WitnessMap
)

sealed class StepResult {
    data class NextChain(val chain: Chain) : StepResult()
    object UnUnlinkedHeader : StepResult()
}

fun Chain.isValidBlockTime(blockTime: BlockTime): Boolean {
    TODO()
}

data class WitnessMap(
    val map: Map<WitnessLabel, Witness>
) {
    fun findWitness(label: WitnessLabel): Witness = TODO()
}

data class ActionDataList(
    val actions: List<ActionData>
) {
    val hashValue: HashValue by lazy {
        val todo = actions.fold("", { hash, action -> "$hash:$action" })
        HashValue(todo)
    }
}

fun step(chain: Chain, block: Block): Chain {
    require(block.header.previousBlockHash == chain.tallBlockHash)
    require(chain.isValidBlockTime(block.header.blockTime))
    require(block.header.actionListHash == block.actionDataList.hashValue)
    val initial = chain.chainState.incrementHeight()
    val balance = block.actionDataList.actions.fold(initial, ::step)
}

fun step(chainState: ChainState, actionData: ActionData): ChainState {
    require(chainState.height >= actionData.minHeight)
    when (actionData) {
        is ActionData.AddInk -> actionData.step(chainState,)
        is ActionData.MoveInk -> TODO()
        is ActionData.WriteDot -> TODO()
        is ActionData.SmashPen -> TODO()
    }
}
