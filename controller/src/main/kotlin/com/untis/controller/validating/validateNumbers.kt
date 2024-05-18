package com.untis.controller.validating

import com.untis.model.exception.RequestException

fun validatePositive(num: Int, field: String) {
    if(num <= 0) throw RequestException.ParamsBad("Field '$field' must be positive")
}