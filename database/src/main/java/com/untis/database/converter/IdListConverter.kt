package com.untis.database.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
internal class IdListConverter : AttributeConverter<List<Long>?, String> {

    override fun convertToDatabaseColumn(attribute: List<Long>?) =
        if (attribute == null) "null"
        else if (attribute.isEmpty()) ""
        else attribute.joinToString(",")

    override fun convertToEntityAttribute(dbData: String?) =
        if (dbData == null) null
        else if (dbData.isBlank()) emptyList()
        else dbData.split(",").map(String::toLong)

}