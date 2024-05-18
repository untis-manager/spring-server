package com.untis.controller.validating

import com.untis.model.exception.RequestException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.regex.Pattern

/**
 * Validates that a string conforms to a gender string, or a custom one like '(s1/s2/s3)'
 */
fun validateGenderString(str: String) {
    if (!isValidGenderString(str)) throw RequestException.WrongStringFormat(
        str,
        "gender"
    )
}

fun validateUUIDString(str: String) {
    if (!isValidUUIDString(str)) throw RequestException.WrongStringFormat(
        str,
        "uuid"
    )
}

fun validateEmail(str: String) {
    if (!isValidEmail(str)) throw RequestException.WrongStringFormat(
        str,
        "email"
    )
}

fun validateLocalDate(date: String) {
    if(!isValidLocalDate(date)) throw RequestException.WrongStringFormat(
        date,
        "ISO-8601 Local date"
    )
}

fun validateLocalTime(time: String) {
    if(!isValidLocalTime(time)) throw RequestException.WrongStringFormat(
        time,
        "ISO-8601 Local time"
    )
}

fun validateLocalDateTime(dateTime: String) {
    if(!isValidLocalDateTime(dateTime)) throw RequestException.WrongStringFormat(
        dateTime,
        "ISO-8601 Local date-time"
    )
}

private fun isValidGenderString(str: String) =
    str == "male" || str == "female" || str == "not_say" || (
            str.startsWith("(")
            && str.endsWith(")")
            && str.substring(1, str.length - 1).count { it == '/' } == 2
            && str.substring(1, str.length - 1).split("/").all { it.trim().isNotEmpty() }
            )

private fun isValidUUIDString(str: String): Boolean {
    try {
        UUID.fromString(str)
        return true
    } catch (_: Exception) {
        return false
    }
}

private fun isValidLocalDate(string: String): Boolean {
    try {
        LocalDate.parse(string)
        return true
    }catch (_: Exception) {
        return false
    }
}

private fun isValidLocalDateTime(string: String): Boolean {
    try {
        LocalDateTime.parse(string)
        return true
    }catch (_: Exception) {
        return false
    }
}

private fun isValidLocalTime(string: String): Boolean {
    try {
        LocalTime.parse(string)
        return true
    }catch (_: Exception) {
        return false
    }
}

private val EmailPattern = Pattern.compile(
    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
)

private fun isValidEmail(str: String) = EmailPattern.matcher(str).matches()