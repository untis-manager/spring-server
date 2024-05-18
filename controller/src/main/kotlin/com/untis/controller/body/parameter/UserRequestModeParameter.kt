package com.untis.controller.body.parameter

/**
 * Defines how much of the user data is expected.
 *
 * May required different permission levels
 */
enum class UserRequestModeParameter {

    /**
     * Only name and birthday will be returned
     */
    Partial,

    /**
     * Whole data will be returned
     */
    Full

}