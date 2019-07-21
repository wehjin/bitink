package com.rubyhuntersky.chain.ink

import com.rubyhuntersky.chain.witness.WitnessLabel
import com.rubyhuntersky.chain.block.BlockHeight
import com.rubyhuntersky.chain.block.Carry
import com.rubyhuntersky.chain.basics.HashValue
import com.rubyhuntersky.chain.basics.PenName
import com.rubyhuntersky.chain.basics.Quantity
import com.rubyhuntersky.chain.witness.WitnessMap
import com.rubyhuntersky.chain.toHashValue

sealed class Surface(typeId: Int) {
    init {
        require(typeId in 1..4)
    }

    abstract fun checkOutput(carry: Carry)

    object Unbreakable : Surface(1) {
        override fun checkOutput(carry: Carry) = Unit
    }

    object Fragile : Surface(2) {
        override fun checkOutput(carry: Carry) = Unit
    }

    data class RequiresWitness(
        val penName: PenName
    ) : Surface(3) {
        override fun checkOutput(carry: Carry) = Unit
    }

    data class RequiresKey(
        val releaseKeyHash: HashValue,
        val releasePenName: PenName,
        val returnPenName: PenName,
        val returnAfterHeight: BlockHeight
    ) : Surface(4) {
        override fun checkOutput(carry: Carry) = check(returnAfterHeight >= carry.blockHeight)
    }
}

sealed class Dip(typeId: Int) {
    init {
        require(typeId in 1..2)
    }

    abstract val inkRef: InkRef

    fun checkInput(carry: Carry, witnessMap: WitnessMap, actionHash: HashValue): Ink =
        carry.findWetInk(inkRef)
            .also {
                check(it.quantity > Quantity.ZERO)
                checkDipIntoSurface(this, it.surface, carry, witnessMap, actionHash)
                check(it.dryHeight > carry.blockHeight)
            }

    data class Witness(
        override val inkRef: InkRef,
        val witnessLabel: WitnessLabel
    ) : Dip(1)

    data class KeyAndWitness(
        override val inkRef: InkRef,
        val releaseKey: String,
        val witnessLabel: WitnessLabel
    ) : Dip(2)
}

private fun checkDipIntoSurface(
    dip: Dip,
    surface: Surface,
    carry: Carry,
    witnessMap: WitnessMap,
    actionHash: HashValue
) {
    when (surface) {
        Surface.Unbreakable -> check(false) { "Unbreakable surface" }
        Surface.Fragile -> {
            require(dip is Dip.Witness)
            witnessMap.findWitness(dip.witnessLabel).also {
                it.checkSignsHash(actionHash)
            }
        }
        is Surface.RequiresWitness -> {
            require(dip is Dip.Witness)
            witnessMap.findWitness(dip.witnessLabel).also {
                it.checkSignsHash(actionHash)
                check(it.publicName == surface.penName)
            }
        }
        is Surface.RequiresKey -> when (dip) {
            is Dip.Witness -> {
                check(carry.blockHeight >= surface.returnAfterHeight)
                witnessMap.findWitness(dip.witnessLabel).also {
                    it.checkSignsHash(actionHash)
                    check(it.publicName == surface.returnPenName)
                }
            }
            is Dip.KeyAndWitness -> {
                check(carry.blockHeight < surface.returnAfterHeight)
                check(dip.releaseKey.toHashValue() == surface.releaseKeyHash)
                witnessMap.findWitness(dip.witnessLabel).also {
                    it.checkSignsHash(actionHash)
                    check(it.publicName == surface.releasePenName)
                }
            }
        }
    }
}

