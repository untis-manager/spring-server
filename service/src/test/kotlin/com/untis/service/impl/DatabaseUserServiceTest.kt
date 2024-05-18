package com.untis.service.impl

import com.untis.test.ObjectGenerator
import com.untis.test.mock.database.TestCourseRepository
import com.untis.test.mock.database.TestRoleRepository
import com.untis.test.mock.database.TestUserRepository
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseUserServiceTest {

    private val userRepository = TestUserRepository()
    private val roleRepository = TestRoleRepository()
    private val courseRepository = TestCourseRepository()

    private val userService = DatabaseUserService(userRepository, roleRepository, courseRepository)

    @BeforeAll
    fun before() {
        val users = (1..10).map { key ->
            ObjectGenerator.Entities.genUser(
                key = key,
                groups = (key..(key + 5)).map { ObjectGenerator.Entities.genGroup(it) }.toSet()
            )
        }.toSet()

        val courses = (1..50).map { key ->
            ObjectGenerator.Entities.genCourse(
                key = key,
                groups = (key..(key + 2)).map { ObjectGenerator.Entities.genGroup(it) }.toSet()
            )
        }.toSet()

        userRepository.saveAll(users)
        courseRepository.saveAll(courses)
    }

    @Test
    fun getAll() {
        val all = userService.getAll()

        assertEquals(10, all.size)
    }

    @Test
    fun getById() {
        val user1 = userService.getById(1)
        val user7 = userService.getById(7)

        assertEquals(1L, user1.id)
        assertEquals(7L, user7.id)
    }

    @Test
    fun getByEmail() {
        val user1 = userService.getByEmail("email 1")
        val user7 = userService.getByEmail("email 7")
        val user11 = userService.getByEmail("email 11")

        assertEquals(1L, user1!!.id)
        assertEquals(7L, user7!!.id)
        assertEquals(null, user11?.id)
    }

    @Test
    fun getByRole() {
        val user1 = userService.getByRole(1)
        val user7 = userService.getByRole(7)
        val user11 = userService.getByRole(11)

        assertEquals(1, user1.size)
        assertEquals(1, user7.size)
        assertEquals(0, user11.size)
    }

    @Test
    fun getInGroup() {
        val inGroup3 = userService.getInGroup(3)
        val inGroup7 = userService.getInGroup(7)
        val inGroup20 = userService.getInGroup(20)

        assertEquals(3, inGroup3.size)
        assertEquals(6, inGroup7.size)
        assertEquals(0, inGroup20.size)
    }

    @Test
    fun existsById() {
        val exists4 = userService.existsById(4)
        val exists7 = userService.existsById(7)
        val exists20 = userService.existsById(7)

        assertEquals(true, exists4)
        assertEquals(true, exists7)
        assertEquals(true, exists20)
    }

    @Test
    fun getEnrolledCourses() {
        // Course 5 -> groups (5, 6, 7)
        // Group 5 -> courses (3, 4, 5)

        val user5 = userService.getEnrolledCourses(5)

        // User 5 in groups (5, 6, 7, 8, 9, 10)
        // Group 5 in courses (3, 4, 5)
        // Group 6 in courses (4, 5, 6)
        // ...
        // Group 10 in courses (8, 9, 10)
        // User should be in courses (3, 4, 5, 6, 7, 8, 9, 10)

        val expected = listOf(3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L).toLongArray()
        val actual = user5.map { it.id!! }.sortedBy { it }.toLongArray()
        assertArrayEquals(expected, actual)
    }

    @Test
    fun create() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun update() {
    }
}