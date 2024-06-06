package com.untis.controller.rest.test

import com.untis.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test/")
class TestController @Autowired constructor(

    val groupService: GroupService

) {

    @GetMapping("{id}/")
    fun test(
        @PathVariable id: Long
    ) = (id * 10).toString()

}