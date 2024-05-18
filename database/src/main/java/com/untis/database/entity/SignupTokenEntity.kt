package com.untis.database.entity

import com.untis.database.converter.IdListConverter
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity(name = "signup_token")
@Table(name = "signup_token")
class SignupTokenEntity(

    /*
    Meta data
     */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @GeneratedValue(strategy = GenerationType.UUID)
    var token: UUID? = null,

    @Column(name = "times_used")
    var timesUsed: Int? = null,

    @Column(name = "multi_use")
    var isMultiUse: Boolean? = null,

    @Column(name = "expiration_date")
    var expirationDate: LocalDateTime? = null,

    /*
    Actual data for the user
     */

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "street")
    var street: String? = null,

    @Column(name = "house_number")
    var houseNumber: String? = null,

    @Column(name = "city")
    var city: String? = null,

    @Column(name = "zip_code")
    var zipCode: String? = null,

    @Column(name = "country")
    var country: String? = null,

    @Column(name = "gender")
    var gender: String? = null,

    @Column(name = "birthday")
    var birthday: LocalDate? = null,

    @Column(name = "role_id", nullable = false)
    var roleId: Long? = null,

    @Column(name = "group_ids", nullable = false)
    @Convert(converter = IdListConverter::class)
    var groupIds: List<Long>? = null

)