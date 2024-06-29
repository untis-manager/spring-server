package com.untis.controller.body.response.config

data class OrganizationNameResponse (

    val organizationName: String

) {

    companion object {

        fun create(name: String) = OrganizationNameResponse(name)

    }

}
