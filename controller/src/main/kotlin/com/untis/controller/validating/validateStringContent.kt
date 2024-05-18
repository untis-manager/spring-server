package com.untis.controller.validating

import com.untis.model.exception.RequestException

fun validateName(name: String) =
    validateStringContent(name, "firstname/lastname", 1..256, UppercaseChars + LowercaseChars)

fun validatePassword(password: String) =
    validateStringContent(password, "password", 1..256, UppercaseChars + LowercaseChars + Numbers + Signs)

fun validateAttachmentFilename(filename: String?) {
    val valid = filename != null &&
            filename.count { it == '.' } == 1 &&
            filename.split('.').all { part ->
                part.all { it in UppercaseChars || it in LowercaseChars || it in Numbers }
            }

    if (!valid) throw RequestException.WrongStringFormat(
        filename.toString(),
        "Attachment filename: exactly one '.', only uppercase, lowercase chars and numbers."
    )
}

private val UppercaseChars = 'A'..'Z' + 'ß'.code
private val LowercaseChars = 'a'..'z'
private val Numbers = '0'..'9'
private val Signs = listOf('@', '€', '!', '"', '§', '%', '&', '/', '(', ')', '=', '?', 'ß')

private fun validateStringContent(
    entered: String,
    fieldName: String,
    lengthRange: IntRange,
    allowedChars: List<Char>
) {
    if (entered.length !in lengthRange) {
        throw RequestException.StringLength(entered, lengthRange, fieldName)
    }
    if (entered.any { it !in allowedChars }) {
        throw RequestException.FieldInvalid(entered, fieldName, allowedChars)
    }
}