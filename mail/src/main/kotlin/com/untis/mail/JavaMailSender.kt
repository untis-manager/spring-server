package com.untis.mail

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl


@Configuration
class MailConfig {

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.ionos.de"
        mailSender.port = 465

        mailSender.username = "simon@bumiller.me"
        mailSender.password = "74Ar/^aZGc_Mb-"

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.ssl.enable"] = "true"
        props["mail.debug"] = "true"

        return mailSender
    }

}