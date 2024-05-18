package com.untis.model

/**
 * Modes that define how users can sign up to the server
 */
sealed class SignUpMode(

    /**
     * The name of the mode to be persisted
     */
    val name: String

) {

    /**
     * States that users can log in freely
     */
    data class Free(

        /**
         * The id of the role a user is given when signing up
         */
        val defaultRoleId: Long,

        /**
         * Whether a user needs to verify their email when signing up
         */
        val needEmailVerification: Boolean

    ) : SignUpMode(Names.Free)

    /**
     * States that users can only be added by already existing users with the necessary permissions
     */
    data object Admin : SignUpMode(Names.Admin)

    /**
     * States that users can only sign up using predefined signup tokens
     */
    data object Token : SignUpMode(Names.Token)

    @Suppress("ConstPropertyName")
    object Names {

        const val Free = "free"
        const val Admin = "admin"
        const val Token = "token"

        fun all() = setOf(Free, Admin, Token)

    }

}