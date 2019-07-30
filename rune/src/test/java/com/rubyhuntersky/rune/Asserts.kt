package com.rubyhuntersky.rune

import com.rubyhuntersky.rune.basics.toHex
import org.junit.jupiter.api.Assertions

fun assertEqualsByteArray(expected: ByteArray, actual: ByteArray) {
    Assertions.assertEquals(expected.toHex(), actual.toHex())
}