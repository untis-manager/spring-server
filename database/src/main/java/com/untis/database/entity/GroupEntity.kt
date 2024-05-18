package com.untis.database.entity

import jakarta.persistence.*

/**
 * A group, that bundles together several users in logical groups.
 * A user can be a member of multiple groups
 */
@Entity(name = "groups")
@Table(name = "groups")
class GroupEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null

)