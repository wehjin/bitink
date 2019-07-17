package com.rubyhuntersky.chain


data class GenesisId(val headerHash: String)

data class Record(
    val genesis: Chain.Genesis = Chain.Genesis(),
    val longestChain: Chain = genesis
)

sealed class Chain {

    abstract val height: BlockHeight
    abstract val id: ChainId
    abstract val balance: ChainBalance

    data class Genesis(
        override val height: BlockHeight = BlockHeight(0)
    ) : Chain() {
        override val id: ChainId get() = TODO("not implemented")
        override val balance: ChainBalance get() = ChainBalance(height, emptySet())
    }

    data class Modern(
        val body: Chain,
        val tail: Block
    ) : Chain() {
        override val height: BlockHeight get() = tail.header.height
        override val id: ChainId get() = tail.header.id
        override val balance: ChainBalance get() = tail.applyActions(body.balance)
    }
}

data class Block(
    val header: BlockHeader,
    val body: BlockBody
) {
    fun isValid(previousChain: Chain): Boolean =
        header.bodyHash == body.hash && header.isValid(previousChain) && body.isValid(previousChain, header)

    fun applyActions(balance: ChainBalance): ChainBalance = body.applyActions(balance)
}


data class BlockHeader(
    val height: BlockHeight,
    val previousChainId: ChainId,
    val blockTime: BlockTime,
    val bodyHash: String,
    val nonce: Nonce,
    val historicalHash: String
) {
    val id: ChainId get() = TODO()

    fun isValid(previousChain: Chain): Boolean =
        height == (previousChain.height.next())
                && previousChainId == previousChain.id
                && blockTime.isValid(previousChain)
}

data class BlockBody(
    val actions: List<ChainAction>
) {
    val hash: String get() = TODO()

    fun isValid(previousChain: Chain, header: BlockHeader): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun applyActions(balance: ChainBalance): ChainBalance = actions.fold(balance, ChainBalance::applyAction)
}

sealed class AddBlockResult {
    object Invalid : AddBlockResult()
}

class Recorder(private val startRecord: Record) {

    var record = startRecord

    fun addBlock(block: Block): AddBlockResult {
        val isValid = block.isValid(record.longestChain)
    }
}