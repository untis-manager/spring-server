package com.untis.controller.exception

import com.untis.model.exception.RequestException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RequestExceptionHandler {

    @ExceptionHandler(RequestException::class)
    fun handleRequestException(exception: RequestException) = ResponseEntity(exception.details, exception.statusCode)

}