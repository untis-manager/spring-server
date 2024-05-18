package com.untis.database.entity

import jakarta.persistence.*

/**
 * A course than users can be enrolled in via groups.
 */
@Entity(name = "courses")
@Table(name = "courses")
class CourseEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "description", nullable = false)
    var description: String? = null,

    /*
    Timing of the course
     */

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "course_id",
    )
    var timings: Set<CourseTimingEntity>? = null,

    /*
    Leaders of the course
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "leaders_courses",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "leader_id")]
    )
    var leaders: Set<UserEntity>? = null,

    /*
    Groups enrolled in this course
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "course_groups",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: MutableSet<GroupEntity>? = null


)