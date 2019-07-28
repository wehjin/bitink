package com.rubyhuntersky.rune

import org.junit.jupiter.api.Test

internal class VarStringTest {

    @Test
    internal fun bytes() {

        val bytes = varStringFixtureA.bytes
        assertEqualsByteArray(varStringFixtureABytes, bytes)
    }
}

