package com.rubyhuntersky.chain.action

import com.rubyhuntersky.chain.block.Carry
import com.rubyhuntersky.chain.block.BlockHeight
import com.rubyhuntersky.chain.basics.HashValue
import com.rubyhuntersky.chain.basics.PenName
import com.rubyhuntersky.chain.basics.Quantity
import com.rubyhuntersky.chain.witness.WitnessLabel
import com.rubyhuntersky.chain.witness.WitnessMap
import com.rubyhuntersky.chain.dot.DotData
import com.rubyhuntersky.chain.ink.Dip
import com.rubyhuntersky.chain.ink.Ink


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

    private fun baseStep(carry: Carry, witnessMap: WitnessMap, mintSize: Quantity): Carry {
        require(carry.blockHeight >= minHeight)
        val inputInks = inputDips.map { it.checkInput(carry, witnessMap, hashValue) }
        outputInks.forEach { it.checkOutput(carry) }
        check(feeSize >= Quantity.ZERO)
        val inputQuantity = inputInks.map(Ink::quantity).fold(Quantity.ZERO, Quantity::plus)
        val outputQuantity = outputInks.map(Ink::quantity).fold(Quantity.ZERO, Quantity::plus)
        check(inputQuantity + mintSize == outputQuantity + feeSize)
        return carry.moveInk(to = outputInks, from = inputInks)
    }

    abstract fun step(carry: Carry, witnessMap: WitnessMap): Carry

    data class AddInk(
        override val minHeight: BlockHeight,
        val outputInk: Ink
    ) : ActionData(typeId = 1) {

        override val inputDips: List<Dip> get() = emptyList()
        override val outputInks: List<Ink> get() = listOf(outputInk)
        override val feeSize: Quantity get() = Quantity.ZERO

        override fun step(carry: Carry, witnessMap: WitnessMap): Carry {
            // TODO Rule out burned pens and limit list lengths
            return super.baseStep(carry, witnessMap, carry.rewardQuantity)
        }
    }

    data class MoveInk(
        override val minHeight: BlockHeight,
        override val inputDips: List<Dip>,
        override val outputInks: List<Ink>,
        override val feeSize: Quantity
    ) : ActionData(typeId = 2) {

        override fun step(carry: Carry, witnessMap: WitnessMap): Carry {
            // TODO Rule out burned pens and limit list lengths
            return super.baseStep(carry, witnessMap, mintSize = Quantity.ZERO)
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

        override fun step(carry: Carry, witnessMap: WitnessMap): Carry {
            // TODO Rule out burned pens and limit list lengths
            witnessMap.findWitness(witnessLabel).also {
                it.checkSignsHash(hashValue)
                check(it.publicName == dotData.author)
            }
            return super.baseStep(carry, witnessMap, mintSize = -carry.dotCost).assertDot(dotData)
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

        override fun step(carry: Carry, witnessMap: WitnessMap): Carry {
            // TODO Rule out burned pens and limit list lengths
            // Include current pen in set of burned pens
            witnessMap.findWitness(witnessLabel).also {
                it.checkSignsHash(hashValue)
                check(it.publicName == penName)
            }
            return super.baseStep(carry, witnessMap, mintSize = -carry.burnCost).burnPen(penName)
        }
    }
}

