package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.block.*

sealed class Surface(typeId: Int) {
    init {
        require(typeId in 1..4)
    }

    abstract fun checkOutput(chainState: ChainState)

    object Unbreakable : Surface(1) {
        override fun checkOutput(chainState: ChainState) = Unit
    }

    object Fragile : Surface(2) {
        override fun checkOutput(chainState: ChainState) = Unit
    }

    data class RequiresWitness(
        val penName: PenName
    ) : Surface(3) {
        override fun checkOutput(chainState: ChainState) = Unit
    }

    data class RequiresKey(
        val releaseKeyHash: HashValue,
        val releasePenName: PenName,
        val returnPenName: PenName,
        val returnAfterHeight: BlockHeight
    ) : Surface(4) {
        override fun checkOutput(chainState: ChainState) = check(returnAfterHeight >= chainState.height)
    }
}

sealed class Dip(typeId: Int) {
    init {
        require(typeId in 1..2)
    }

    abstract val inkRef: InkRef

    fun checkInput(chainState: ChainState, witnessMap: WitnessMap, actionHash: HashValue): Ink =
        chainState.findWetInk(inkRef)
            .also {
                check(it.quantity > Quantity.ZERO)
                checkDipIntoSurface(this, it.surface, chainState, witnessMap, actionHash)
                check(it.dryHeight > chainState.height)
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
    chainState: ChainState,
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
                check(chainState.height >= surface.returnAfterHeight)
                witnessMap.findWitness(dip.witnessLabel).also {
                    it.checkSignsHash(actionHash)
                    check(it.publicName == surface.returnPenName)
                }
            }
            is Dip.KeyAndWitness -> {
                check(chainState.height < surface.returnAfterHeight)
                check(dip.releaseKey.toHashValue() == surface.releaseKeyHash)
                witnessMap.findWitness(dip.witnessLabel).also {
                    it.checkSignsHash(actionHash)
                    check(it.publicName == surface.releasePenName)
                }
            }
        }
    }
}

