package com.untis.controller.validating

import com.untis.controller.base.ControllerScope
import com.untis.model.exception.RequestException

fun ControllerScope.validateUserEmailUnique(email: String) {
    if(userService == null) throw IllegalStateException("Can't check for duplicate emails when the 'userService' is not present")
    if(userService!!.getByEmail(email) != null) throw RequestException.NotUnique(email)
}