package com.untis.controller.body.response.group

import com.untis.model.Group

data class GroupHierarchyElement (

    val group: GroupResponse,

    val children: List<GroupResponse>

) {

    companion object {

        fun create(group: Group, children: List<Group>): GroupHierarchyElement {
            val childrenElements = children.map(GroupResponse::create)
            val groupElement = GroupResponse.create(group)
            return GroupHierarchyElement(groupElement, childrenElements)
        }

    }

}

data class GroupHierarchyResponse(

    val groups: List<GroupHierarchyElement>

)
