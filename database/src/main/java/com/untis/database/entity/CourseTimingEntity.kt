package com.untis.database.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

/**
 * Defines a rule as to when the course it's applied to will happen.
 */
@Entity(name = "course_timings")
@Table(name = "course_timings")
class CourseTimingEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    /*
    General information
     */
    @Column(name = "start_time", nullable = false)
    var startTime: LocalTime? = null,

    @Column(name = "end_time", nullable = false)
    var endTime: LocalTime? = null,

    // Break time in minutes
    @Column(name = "break_time", nullable = false)
    var breakTime: Int? = null,

    // Where the course will happen
    // TODO: Maybe have a whole new table for locations, and here a many to one
    @Column(name = "location", nullable = false)
    var location: String? = null,

    /*
    Repeating
     */

    @Column(name = "is_repeating", nullable = false)
    var isRepeating: Boolean = false,

    @Column(name = "repeating_start_date", nullable = true)
    var repeatingStartDate: LocalDate? = null,

    // 1 = Every day, 2 = Every other day, 3 = Every third day, etc...
    @Column(name = "repeating_interval", nullable = true)
    var repeatingInterval: Int? = null,

    @Column(name = "repeating_end_date", nullable = true)
    var repeatingEndDate: LocalDate? = null,

    @Column(name = "repeating_repetitions", nullable = true)
    var repeatingRepetitions: Int? = null,

    /*
    Single
     */

    @Column(name = "is_single", nullable = false)
    var isSingle: Boolean = false,

    @Column(name = "single_date", nullable = true)
    var singleDate: LocalDate? = null

)