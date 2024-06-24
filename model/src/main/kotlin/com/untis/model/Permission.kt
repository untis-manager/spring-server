package com.untis.model

/**
 * Scenarios for a users permission for a specific part of the service
 */
sealed class Permission(

    val type: String,

    open val stage: Int,

    open val maxStage: Int

) : Comparable<Permission> {

    /**
     * Simple permission: User can either only read or read and write
     */
    sealed class Simple(

        /**
         * Numerical representation to compare
         */
        override val stage: Int

    ) : Permission("simple", stage, 2) {

        /**
         * User has permission to read
         */
        data object Read : Simple(1)

        /**
         * User has permission to read and write
         */
        data object Write : Simple(2)

        companion object {

            fun create(stage: Int) = when (stage) {
                1 -> Read
                2 -> Write
                else -> throw IllegalArgumentException("Stage '$stage' for permission type 'simple' is not valid")
            }

        }

    }

    /**
     * Permissions for cases where a user may have access to a subset of all entities
     */
    sealed class Scoped(

        /**
         * Numerical representation to compare
         */
        override val stage: Int

    ) : Permission("scoped", stage, 4) {

        /**
         * User has no access at all
         */
        data object Not : Scoped(1)

        /**
         * User can read entities connected to him
         */
        data object Own : Scoped(2)

        /**
         * User can read all entities
         */
        data object All : Scoped(3)

        /**
         * User can read and edit all entities
         */
        data object Edit : Scoped(4)

        companion object {

            fun create(stage: Int) = when (stage) {
                1 -> Not
                2 -> Own
                3 -> All
                4 -> Edit
                else -> throw IllegalArgumentException("Stage '$stage' for permission type 'scoped' is not valid")
            }

        }

    }

    /**
     * Special case for own profile
     */
    sealed class Profile(

        /**
         * Numerical representation to compare
         */
        override val stage: Int

    ) : Permission("profile", stage, 3) {

        /**
         * User has no access at all
         */
        data object Not : Profile(1)

        /**
         * User can read own profile
         */
        data object Read : Profile(2)

        /**
         * User can edit own profile
         */
        data object Edit : Profile(3)

        companion object {

            fun create(stage: Int) = when (stage) {
                1 -> Not
                2 -> Read
                3 -> Edit
                else -> throw IllegalArgumentException("Stage '$stage' for permission type 'profile' is not valid")
            }

        }

    }

    /**
     * Special case for other users where partial access is possible
     */
    sealed class Users(

        /**
         * Numerical representation to compare
         */
        override val stage: Int

    ) : Permission("users", stage, 4) {

        /**
         * User has no access to other users at all
         */
        data object Not : Users(1)

        /**
         * User can read non-private information about other users
         */
        data object Partial : Users(2)

        /**
         * User can read all information about all users
         */
        data object Full : Users(3)

        /**
         * User can read all information about all users and edit all
         */
        data object Edit : Users(4)

        companion object {

            fun create(stage: Int) = when (stage) {
                1 -> Not
                2 -> Partial
                3 -> Full
                4 -> Edit
                else -> throw IllegalArgumentException("Stage '$stage' for permission type 'users' is not valid")
            }

        }

    }

    /**
     * Checks whether this permission is enough to satisfy the [needed]
     *
     * @param needed The needed permission
     * @return Whether this permission is enough to satisfy the needed permission
     */
    fun satisfies(needed: Permission): Boolean {
        if (needed.type != this.type) throw IllegalArgumentException("Cannot compare different permission types '${needed.type}' and '${this.type}'")
        return this.stage >= needed.stage
    }

    /**
     * Generates all authorities gained by the permission
     *
     * @param name The name of the authority
     * @return The authorities in the format 'SCOPE_STAGE'
     */
    fun authoritiesDownwards(name: String): List<String> = (1..stage).map { "${name}_$it" }

    /**
     * Generates all authorities needed to fulfill the given one
     *
     * @param name The name of the authority
     * @return The authorities in the format 'SCOPE_STAGE'
     */
    fun authoritiesUpwards(name: String): List<String> = (stage..maxStage).map { "${name}_$it" }

    override fun compareTo(other: Permission) =
        if (this.stage > other.stage) 1
    else if (this.stage < other.stage) -1
    else 0

}