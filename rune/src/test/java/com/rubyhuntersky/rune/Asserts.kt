package com.rubyhuntersky.rune

import org.junit.jupiter.api.Assertions

fun assertEqualsByteArray(expected: ByteArray, actual: ByteArray) {
    Assertions.assertEquals(expected.toHex(), actual.toHex())
}