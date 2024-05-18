package com.untis.model.exception

import com.untis.model.SignUpMode
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

/**
 * An exception that is thrown when the input of a query is not valid
 */
sealed class RequestException(

    /**
     * The status code
     */
    val statusCode: HttpStatusCode,

    /**
     * The description of the exception
     */
    val details: String

) : RuntimeException() {

    /**
     * A referenced ID was not found
     *
     * @param id The id that was not found
     * @param type The item type the id refers to
     */
    data class IdNotFound(val id: String, val type: String) :
        RequestException(HttpStatus.NOT_FOUND, "Could not fin id '$id' for type '$type'")


    /**
     * Parameters of a request are invalid
     *
     * @param description Description of the error
     */
    data class ParamsBad(val description: String) : RequestException(HttpStatus.BAD_REQUEST, description)


    /**
     * A string did not conform to an expected format
     *
     * @param string The string passed by the user
     * @param format The format that was expected
     */
    data class WrongStringFormat(val string: String, val format: String) : RequestException(
        HttpStatus.BAD_REQUEST,
        "The passed value '$string' does not conform to the format '$format'"
    )

    /**
     * A string violated the allowed chars bounds
     *
     * @param entered The user entered string
     * @param field The name of the field
     * @param allowedChars The chars that are allowed
     */
    data class FieldInvalid(val entered: String, val field: String, val allowedChars: List<Char>) : RequestException(
        HttpStatus.BAD_REQUEST,
        "The passed input '$entered' for field '$field' contains characters that are not allowed. Allowed are: [${allowedChars.joinToString()}]"
    )

    /**
     * A string was not inside the bound length
     */
    data class StringLength(val entered: String, val range: IntRange, val field: String) : RequestException(
        HttpStatus.BAD_REQUEST,
        "The value '$entered' for field '$field' did not match the range (min: ${range.first} max: ${range.last}"
    )

    /**
     * A value is already used.
     */
    data class DuplicateEntry(val entered: String, val type: String) : RequestException(
        HttpStatus.CONFLICT,
        "The value '$entered' for type '$type' can not be used as that is already occupied."
    )

    /**
     * The user called an endpoint which requires a different [SignUpMode] to be active.
     *
     * @param actual The enabled [SignUpMode]
     */
    data class WrongSignUpMode(val actual: SignUpMode) : RequestException(
        HttpStatus.CONFLICT,
        "The requested action is not allowed as the current sign up mode is '$actual'"
    )

    /**
     * The user assumed that email verification is needed.
     */
    @Suppress("JavaIoSerializableObjectMustHaveReadResolve")
    data object NoEmailVerificationNeeded : RequestException(HttpStatus.CONFLICT, "No email verification is needed")

    /**
     * A token that was passed by the user is not valid for any reason.
     *
     * @param token The uuid of the token
     */
    data class InvalidToken(val token: String) :
        RequestException(HttpStatus.UNAUTHORIZED, "The token '$token' is not valid.")

    /**
     * A param the client passed is invalid as it has already been used
     *
     * @param item The param the user passed
     */
    data class NotUnique(val item: String) :
        RequestException(HttpStatus.CONFLICT, "The item '$item' has already been chosen.")
}