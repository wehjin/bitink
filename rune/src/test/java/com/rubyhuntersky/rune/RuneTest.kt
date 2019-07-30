package com.rubyhuntersky.rune

import com.rubyhuntersky.rune.rune.Rune
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class RuneTest {

    @Nested
    inner class Building {

        @Test
        internal fun spirit() {
            val rune = Rune.Spirit.valueOf("bc1a")
            assertNotNull(rune)
        }

        @Test
        internal fun content() {
            val rune = Rune.Content.valueAt("QmWATWQ7fVPP2EFGu71UkfnqhYXDYH566qy47CnJDgvs8u")
            assertNotNull(rune)
        }

        @Test
        internal fun text() {
            val rune = Rune.Text.valueOf("a")
            assertNotNull(rune)
        }

        @Test
        internal fun number() {
            val rune = Rune.Number.valueOf(3)
            assertNotNull(rune)
        }

        @Test
        internal fun truth() {
            val rune = Rune.Truth.valueOf(true)
            assertNotNull(rune)
        }
    }
}