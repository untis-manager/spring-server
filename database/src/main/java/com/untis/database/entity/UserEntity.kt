package com.untis.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate

@Entity(
    name = "users"
)
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class UserEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "email", unique = true, nullable = false)
    var email: String? = null,

    @Column(name = "first_name", nullable = false)
    var firstName: String? = null,

    /*
    Name information
     */

    @Column(name = "last_name", nullable = false)
    var lastName: String? = null,

    @Column(name = "password", nullable = false)
    var password: String? = null,

    /*
    Address information
     */

    @Column(name = "street", nullable = false)
    var street: String? = null,

    @Column(name = "house_number", nullable = false)
    var houseNumber: String? = null,

    @Column(name = "city", nullable = false)
    var city: String? = null,

    @Column(name = "zip_code", nullable = false)
    var zipCode: String? = null,

    @Column(name = "country", nullable = false)
    var country: String? = null,

    /*
    General information
     */
    @Column(name = "gender", nullable = false)
    var gender: String? = null,

    @Column(name = "birthday", nullable = false)
    var birthday: LocalDate? = null,

    /*
    Groups
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: MutableSet<GroupEntity>? = null



)