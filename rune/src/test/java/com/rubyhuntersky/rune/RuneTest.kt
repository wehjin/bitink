package com.rubyhuntersky.rune

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class RuneTest {

    @Nested
    inner class Building {

        @Test
        internal fun identity() {
            val rune = Rune.Creature.valueOf("bc1a")
            assertNotNull(rune)
        }

        @Test
        internal fun noid() {
            val rune = Rune.Place.valueOf("QmWATWQ7fVPP2EFGu71UkfnqhYXDYH566qy47CnJDgvs8u")
            assertNotNull(rune)
        }

        @Test
        internal fun numberThing() {
            val rune = Rune.Thing.valueOf(3)
            assertNotNull(rune)
        }

        @Test
        internal fun textThing() {
            val rune = Rune.Thing.valueOf("a")
            assertNotNull(rune)
        }
    }
}