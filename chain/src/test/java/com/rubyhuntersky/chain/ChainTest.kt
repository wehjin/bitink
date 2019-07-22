package com.rubyhuntersky.chain

import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ChainTest : Spek({

    describe("Chain") {
        it("changes math") {
            assertEquals(1, 2)
        }
    }
})