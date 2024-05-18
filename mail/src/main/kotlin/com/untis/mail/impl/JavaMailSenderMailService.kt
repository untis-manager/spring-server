package com.untis.mail.impl

import com.untis.mail.MailService
import com.untis.model.SecurityToken
import com.untis.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
internal class JavaMailSenderMailService @Autowired constructor(

    val mailSender: JavaMailSender

) : MailService {

    private fun sendBaseEmail(
        recipient: String,
        subject: String,
        text: String,
    ) = SimpleMailMessage().apply {
        setTo(recipient)
        from = "simon@bumiller.me"
        setSubject(subject)
        setText(text)
    }.let(mailSender::send)

    override fun sendPasswordResetToken(user: User, token: SecurityToken) = sendBaseEmail(
        recipient = user.email,
        subject = "Reset your password",
        text = "To reset your password, use the following token:\n${token.token}"
    )

    override fun sendEmailResetToken(user: User, token: SecurityToken) = sendBaseEmail(
        recipient = user.email,
        subject = "Verify new email address",
        text = "To verify your new email address, use the following token:\n${token.token}"
    )

    override fun sendEmailConfirmToken(email: String, token: SecurityToken) = sendBaseEmail(
        recipient = email,
        subject = "Verify your email address",
        text = "To verify your email address, use the following token:\n${token.token}"
    )

}