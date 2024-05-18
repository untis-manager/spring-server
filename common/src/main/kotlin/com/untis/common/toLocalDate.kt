package com.untis.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun String.toLocalDate(): LocalDate = LocalDate.parse(this)

fun String.toLocalTime(): LocalTime = LocalTime.parse(this)

fun String.toLocalDateTime(): LocalDateTime = LocalDateTime.parse(this)