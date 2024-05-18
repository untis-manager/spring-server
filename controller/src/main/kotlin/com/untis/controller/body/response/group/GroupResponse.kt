package com.untis.controller.body.response.group

import com.untis.model.Group

data class GroupResponse(

    val id: Long,

    val name: String

) {

    companion object {

        fun create(group: Group) = GroupResponse(
            group.id!!,
            group.name
        )

    }

}
