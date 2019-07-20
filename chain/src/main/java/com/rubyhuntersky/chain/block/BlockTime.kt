package com.rubyhuntersky.chain.block

import com.rubyhuntersky.chain.Chain

data class BlockTime(val long: Long) {
    fun isValid(previousChain: Chain): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}