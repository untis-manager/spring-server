package com.untis.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

/**
 * A group, that bundles together several users in logical groups.
 * A user can be a member of multiple groups
 */
@Entity(name = "groups")
@Table(name = "groups")
class GroupEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @OneToOne(
        fetch = FetchType.EAGER,
        mappedBy = "permissions_id",
        optional = false
    )
    var permissions: GroupPermissionsEntity? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_group_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var parentGroup: GroupEntity? = null,

    @OneToMany(
        cascade = [CascadeType.ALL],
        mappedBy="parentGroup",
        orphanRemoval = true
    )
    var directChildrenGroups: List<GroupEntity>? = null

)