package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.*
import com.rubyhuntersky.chain.block.ChainState
import com.rubyhuntersky.chain.block.BlockHeight
import com.rubyhuntersky.chain.block.HashValue
import com.rubyhuntersky.chain.block.WitnessMap


sealed class ActionData(val typeId: Int) {
    init {
        require(typeId in 1..4)
    }

    abstract val minHeight: BlockHeight
    abstract val inputDips: List<Dip>
    abstract val outputInks: List<Ink>
    abstract val feeSize: Quantity

    val hashValue: HashValue
        get() = TODO()

    private fun baseStep(chainState: ChainState, witnessMap: WitnessMap, mintSize: Quantity): ChainState {
        require(chainState.height >= minHeight)
        val inputInks = inputDips.map { it.checkInput(chainState, witnessMap, hashValue) }
        outputInks.forEach { it.checkOutput(chainState) }
        check(feeSize >= Quantity.ZERO)
        val inputQuantity = inputInks.map(Ink::quantity).fold(Quantity.ZERO, Quantity::plus)
        val outputQuantity = outputInks.map(Ink::quantity).fold(Quantity.ZERO, Quantity::plus)
        check(inputQuantity + mintSize == outputQuantity + feeSize)
        return chainState.moveInk(to = outputInks, from = inputInks)
    }

    abstract fun step(chainState: ChainState, witnessMap: WitnessMap): ChainState

    data class AddInk(
        override val minHeight: BlockHeight,
        val outputInk: Ink
    ) : ActionData(typeId = 1) {

        override val inputDips: List<Dip> get() = emptyList()
        override val outputInks: List<Ink> get() = listOf(outputInk)
        override val feeSize: Quantity get() = Quantity.ZERO

        override fun step(chainState: ChainState, witnessMap: WitnessMap): ChainState {
            // TODO Rule out burned pens and limit list lengths
            return super.baseStep(chainState, witnessMap, chainState.rewardQuantity)
        }
    }

    data class MoveInk(
        override val minHeight: BlockHeight,
        override val inputDips: List<Dip>,
        override val outputInks: List<Ink>,
        override val feeSize: Quantity
    ) : ActionData(typeId = 2) {

        override fun step(chainState: ChainState, witnessMap: WitnessMap): ChainState {
            // TODO Rule out burned pens and limit list lengths
            return super.baseStep(chainState, witnessMap, mintSize = Quantity.ZERO)
        }
    }

    data class WriteDot(
        override val minHeight: BlockHeight,
        val dotData: DotData,
        val witnessLabel: WitnessLabel,
        override val inputDips: List<Dip>,
        val overflowInk: Ink,
        override val feeSize: Quantity
    ) : ActionData(typeId = 3) {

        override val outputInks: List<Ink> get() = listOf(overflowInk)

        override fun step(chainState: ChainState, witnessMap: WitnessMap): ChainState {
            // TODO Rule out burned pens and limit list lengths
            witnessMap.findWitness(witnessLabel).also {
                it.checkSignsHash(hashValue)
                check(it.publicName == dotData.author)
            }
            return super.baseStep(chainState, witnessMap, mintSize = -chainState.dotCost).assertDot(dotData)
        }
    }

    data class SmashPen(
        override val minHeight: BlockHeight,
        val penName: PenName,
        val witnessLabel: WitnessLabel,
        override val inputDips: List<Dip>,
        val overflowInk: Ink,
        override val feeSize: Quantity
    ) : ActionData(typeId = 4) {

        override val outputInks: List<Ink> get() = listOf(overflowInk)

        override fun step(chainState: ChainState, witnessMap: WitnessMap): ChainState {
            // TODO Rule out burned pens and limit list lengths
            // Include current pen in set of burned pens
            witnessMap.findWitness(witnessLabel).also {
                it.checkSignsHash(hashValue)
                check(it.publicName == penName)
            }
            return super.baseStep(chainState, witnessMap, mintSize = -chainState.burnCost).burnPen(penName)
        }
    }
}

