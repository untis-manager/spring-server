package com.untis.database.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.time.LocalTime

/**
 * Information about a specific instance of a course.
 * These are bound to their course type via a many-to-one relationship and defined by the date and start time
 */
@Entity(name = "course_instance_info")
@Table(name = "course_instance_info")
class CourseInstanceInfoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var notes: String? = null,

    /*
    modes
     */
    @Column(name = "is_canceled", nullable = false)
    var isCanceled: Boolean? = null,

    @Column(name = "is_location_change", nullable = false)
    var isLocationChange: Boolean? = null,

    @Column(name = "changed_location", nullable = true)
    var changedLocation: String? = null,

    @Column(name = "is_leader_change", nullable = false)
    var isLeaderChange: Boolean? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "course_instance_info_changed_leaders",
        joinColumns = [JoinColumn(name = "instance_info_id")],
        inverseJoinColumns = [JoinColumn(name = "leader_id")]
    )
    var changedLeaders: List<UserEntity>? = null,

    /*
    date and startTime are used to identify an instance info
     */

    @Column(name = "date", nullable = false)
    var date: LocalDate? = null,

    @Column(name = "start_time", nullable = false)
    var startTime: LocalTime? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var course: CourseEntity? = null

)