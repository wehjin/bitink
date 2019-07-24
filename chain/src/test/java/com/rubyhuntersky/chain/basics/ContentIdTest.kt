package com.rubyhuntersky.chain.basics

import io.ipfs.cid.Cid
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ContentIdTest {

    @Nested
    inner class IpfsVersion0 {

        private val idDecoded: Cid = Cid.decode("QmWATWQ7fVPP2EFGu71UkfnqhYXDYH566qy47CnJDgvs8u")
        private val idEncoded: ByteArray = listOf(
            0x12,
            0x20,
            0x74,
            0x41,
            0x5,
            0x77,
            0x11,
            0x10,
            0x96,
            0xcd,
            0x81,
            0x7a,
            0x3f,
            0xae,
            0xd7,
            0x86,
            0x30,
            0xf2,
            0x24,
            0x56,
            0x36,
            0xbe,
            0xde,
            0xd4,
            0x12,
            0xd3,
            0xb2,
            0x12,
            0xa2,
            0xe0,
            0x9b,
            0xa5,
            0x93,
            0xca
        ).toSignedByteArray()

        private val contentId = ContentId(idDecoded)

        @Test
        internal fun construct() {
            assertEquals("base58btc", contentId.multibase)
            assertEquals("cidv0", contentId.cidVersion)
            assertEquals("dag-pb", contentId.muticodec)
            assertEquals("sha2_256", contentId.multihash)
        }

        @Test
        internal fun write() {
            assertEquals(idEncoded.toHex(), contentId.bytes.toHex())
        }

        @Test
        internal fun read() {
            assertEquals(contentId, ContentId.read(idEncoded))
        }
    }

}