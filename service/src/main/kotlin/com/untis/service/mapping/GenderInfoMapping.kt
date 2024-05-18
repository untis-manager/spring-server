package com.untis.service.mapping

import com.untis.model.GenderInfo

fun GenderInfo.mapToString(): String =
    when (this) {
        is GenderInfo.Custom -> "($greeting/$pronounOne/$pronounTwo)"
        GenderInfo.Female -> "female"
        GenderInfo.Male -> "male"
        GenderInfo.NotSay -> "not_say"
    }

fun createGenderInfo(string: String): GenderInfo = when (string) {
    "male" -> GenderInfo.Male
    "female" -> GenderInfo.Female
    "not_say" -> GenderInfo.NotSay
    else -> string.substring(1, string.length - 1).split("/").run {
        GenderInfo.Custom(component1(), component2(), component3())
    }
}