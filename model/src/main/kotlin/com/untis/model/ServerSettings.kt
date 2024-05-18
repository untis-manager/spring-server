package com.untis.model

/**
 * Settings that are created globally
 */
data class ServerSettings(

    /**
     * The mode how users can sign up to the server
     */
    val signUpMode: SignUpMode

) {

    companion object {

        /**
         * The default server settings
         */
        val Default = ServerSettings(
            signUpMode = SignUpMode.Admin
        )

    }

}
