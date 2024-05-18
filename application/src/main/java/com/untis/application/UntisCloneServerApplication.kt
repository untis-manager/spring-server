package com.untis.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.untis.database"])
@ComponentScan(basePackages = ["com.untis"])
@EntityScan("com.untis.database")
class UntisCloneServerApplication

fun main(args: Array<String>) {
    runApplication<UntisCloneServerApplication>(*args)
}
