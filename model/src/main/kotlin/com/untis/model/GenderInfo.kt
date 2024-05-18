package com.untis.model

/**
 * Information about the gender of a user
 */
sealed class GenderInfo {

    /**
     * Male
     */
    data object Male : GenderInfo()

    /**
     * Female
     */
    data object Female : GenderInfo()

    /**
     * Don't want to say about it
     */
    data object NotSay : GenderInfo()

    data class Custom(

        /**
         * 'Sir' or 'Madam' equiv.
         */
        val greeting: String,

        /**
         * First pronoun
         */
        val pronounOne: String,

        /**
         * Second pronoun
         */
        val pronounTwo: String

    ) : GenderInfo()

}