package com.untis.test

import com.untis.database.entity.*
import com.untis.model.*
import java.time.LocalDate
import java.time.LocalTime

object ObjectGenerator {

    /**
     * Generates a random [LocalDate]
     *
     * @param key The key
     */
    fun genDate(key: Int) = LocalDate.of(2000, 1, 1).plusDays(key.toLong())


    object Entities {

        /**
         * Generates a random [RoleEntity]
         *
         * @param key The key
         */
        fun genRole(key: Int): RoleEntity = RoleEntity(
            id = key.toLong(),
            name = "role $key",
            canEditOwnProfile = key % 2 == 0,
            canEditRoles = key % 3 == 0,
            canViewOtherProfile = key % 5 == 0,
            canEditCourses = key % 7 == 0,
            canEditOtherProfile = key % 11 == 0
        )

        /**
         * Generates a random [GroupEntity]
         *
         * @param key The key
         */
        fun genGroup(key: Int): GroupEntity = GroupEntity(
            id = key.toLong(),
            name = "group $key",
        )

        /**
         * Generates a random gender string
         *
         * @param key The key
         */
        fun genGenderString(
            key: Int
        ) = if (key % 2 == 0) "male"
        else if (key % 3 == 0) "female"
        else if (key % 5 == 0) "not_say"
        else "(greeting $key/pronoun $key/pronoun $key)"

        /**
         * Generates a random [UserEntity]
         *
         * @param key The key
         * @param role The role of the user
         * @param groups The groups of the user
         */
        fun genUser(
            key: Int,
            role: RoleEntity = genRole(key),
            groups: Set<GroupEntity> = (1..10).map(::genGroup).toSet()
        ) = UserEntity(
            id = key.toLong(),
            email = "email $key",
            firstName = "firstname $key",
            lastName = "lastname $key",
            password = "password $key",
            street = "street $key",
            houseNumber = "housenumber $key",
            city = "city $key",
            zipCode = "zipcode $key",
            country = "country $key",
            gender = genGenderString(key),
            birthday = genDate(key),
            role = role,
            groups = groups.toMutableSet()
        )

        /**
         * Generates a random [CourseEntity]
         *
         * @param key The key
         * @param timings The timings
         * @param leaders The leaders of the group
         * @param groups The groups enrolled in this course
         */
        fun genCourse(
            key: Int,
            timings: Set<CourseTimingEntity> = (1..3).map(::genTiming).toSet(),
            leaders: Set<UserEntity> = (1..5).map(::genUser).toSet(),
            groups: Set<GroupEntity> = (1..3).map(::genGroup).toSet(),
        ) = CourseEntity(
            id = key.toLong(),
            name = "course $key",
            description = "description $key",
            timings = timings,
            leaders = leaders,
            groups = groups.toMutableSet()
        )

        fun genTiming(
            key: Int
        ) = if (key % 2 == 0) {
            CourseTimingEntity(
                id = key.toLong(),
                name = "timing 1",
                startTime = LocalTime.of(1, 1, 4).plusMinutes(key.toLong()),
                endTime = LocalTime.of(1, 1, 4).plusMinutes(key.toLong()).plusMinutes(key * 2L),
                breakTime = key * 10,
                isRepeating = true,
                repeatingStartDate = LocalDate.of(2000, 1, 1).plusDays(key.toLong()),
                repeatingInterval = key * 5,
                repeatingEndDate = LocalDate.of(2000, 1, 1).plusDays(key.toLong()).plusMonths(key.toLong()),
                repeatingRepetitions = null,
                isSingle = false,
                singleDate = null
            )
        } else if (key % 3 == 0) {
            CourseTimingEntity(
                id = key.toLong(),
                name = "timing 1",
                startTime = LocalTime.of(1, 1, 4).plusMinutes(key.toLong()),
                endTime = LocalTime.of(1, 1, 4).plusMinutes(key.toLong()).plusMinutes(key * 2L),
                breakTime = key * 10,
                isRepeating = true,
                repeatingStartDate = LocalDate.of(2000, 1, 1).plusDays(key.toLong()),
                repeatingInterval = key * 5,
                repeatingEndDate = null,
                repeatingRepetitions = key * 4,
                isSingle = false,
                singleDate = null
            )
        } else {
            CourseTimingEntity(
                id = key.toLong(),
                name = "timing 1",
                startTime = LocalTime.of(1, 1, 4).plusMinutes(key.toLong()),
                endTime = LocalTime.of(1, 1, 4).plusMinutes(key.toLong()).plusMinutes(key * 2L),
                breakTime = key * 10,
                isRepeating = false,
                repeatingStartDate = null,
                repeatingInterval = null,
                repeatingEndDate = null,
                repeatingRepetitions = null,
                isSingle = true,
                singleDate = LocalDate.of(2000, 1, 1).plusDays(key.toLong())
            )
        }

    }

    object Models {

        /**
         * Generates a random [RolePermissions]
         *
         * @param key The key
         */
        fun genRolePermissions(key: Int) = RolePermissions(
            canEditOwnProfile = key % 2 == 0,
            canEditRoles = key % 3 == 0,
            canViewOtherProfile = key % 5 == 0,
            canEditData = key % 7 == 0,
            canEditOtherProfile = key % 11 == 0,
            canViewAllData = key % 13 == 0
        )

        /**
         * Generates a random [Role]
         *
         * @param key The key
         */
        fun genRole(key: Int) = Role(
            id = key.toLong(),
            name = "role $key",
            permissions = genRolePermissions(key)
        )

        /**
         * Generates a random [Group]
         *
         * @param key The key
         */
        fun genGroup(key: Int) = Group(
            id = key.toLong(),
            name = "group $key",
        )

        /**
         * Generates a random [GenderInfo]
         *
         * @param key The key
         */
        fun genGenderInfo(key: Int) = if (key % 2 == 0) GenderInfo.Male
        else if (key % 3 == 0) GenderInfo.Female
        else if (key % 5 == 0) GenderInfo.NotSay
        else GenderInfo.Custom("greeting $key", "pronoun $key", "pronoun $key")

        /**
         * Generates a random [AddressInfo]
         *
         * @param key The key
         */
        fun genAddressInfo(key: Int) = AddressInfo(
            country = "country $key",
            city = "city $key",
            zip = "zipcode $key",
            street = "street $key",
            houseNumber = "housenumber $key"
        )

        /**
         * Generates a random [User]
         *
         * @param key The key
         */
        fun genUser(key: Int) = User(
            id = key.toLong(),
            email = "email $key",
            firstName = "firstname $key",
            lastName = "lastname $key",
            encodedPassword = "password $key",
            addressInfo = genAddressInfo(key),
            gender = genGenderInfo(key),
            birthDate = genDate(key),
            role = genRole(key)
        )

    }
}