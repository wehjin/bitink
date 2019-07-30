package com.rubyhuntersky.rune

import com.rubyhuntersky.rune.basics.things.VarString
import com.rubyhuntersky.rune.basics.toSignedByteArray

val varStringFixtureA = VarString.valueOf("a")
val varStringFixtureABytes = listOf(0x01, 'a'.toInt()).toSignedByteArray()

val contentIdFixtureHelloWorldV0 = "QmWATWQ7fVPP2EFGu71UkfnqhYXDYH566qy47CnJDgvs8u"
