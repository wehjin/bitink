package com.rubyhuntersky.chain

import com.rubyhuntersky.chain.basics.HashValue

const val MINUTES_PER_HOUR = 60
const val HOURS_PER_DAY = 24
const val DAYS_PER_MONTH = 30
const val MINUTES_PER_MONTH = MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_MONTH


const val GOAL_MINUTES_BETWEEN_BLOCKS = 5
const val GOAL_BLOCKS_PER_MINUTE = 1.0 / GOAL_MINUTES_BETWEEN_BLOCKS

const val INK_DWELL_MONTHS = 6
const val INK_DWELL_BLOCKS = (INK_DWELL_MONTHS * MINUTES_PER_MONTH * GOAL_BLOCKS_PER_MINUTE).toLong()

fun String.toHashValue(): HashValue = TODO()
