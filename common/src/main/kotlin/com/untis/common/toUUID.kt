package com.untis.common

import java.util.UUID

fun String.toUUIDSafe() = try {
    UUID.fromString(this)
} catch (_: Exception) {
    null
}

fun String.toUUID(): UUID = UUID.fromString(this)