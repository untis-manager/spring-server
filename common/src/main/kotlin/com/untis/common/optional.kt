package com.untis.common

import java.util.*

fun <T> T?.optional() = if (this == null) Optional.empty<T>() else Optional.of(this)