package com.untis.model

/**
 * Model of a single course.
 * This is the high-level definition of a course like "Algebra II" or "Spanish" and does not provide any information about
 *      the actual instanced of it, like "Algebra II on Tuesday, 1/1/2000"
 */
data class Course(

    /**
     * The id of the course.
     * Null when it has not yet been saved to the db.
     */
    val id: Long?,

    /**
     * The name of the course
     */
    val name: String,

    /**
     * The description of the course
     */
    val description: String

)