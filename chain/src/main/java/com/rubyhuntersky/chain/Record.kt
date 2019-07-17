package com.rubyhuntersky.chain

data class Record(
    val genesis: Chain.Genesis = Chain.Genesis,
    val longestChain: Chain = genesis
)