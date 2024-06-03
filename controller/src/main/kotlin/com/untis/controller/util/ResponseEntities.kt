package com.untis.controller.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <T> unauthorized() = ResponseEntity<T>(HttpStatus.UNAUTHORIZED)

fun <T> ok(t: T) = ResponseEntity(t, HttpStatus.OK)

fun <T> conflict() = ResponseEntity<T>(HttpStatus.CONFLICT)

fun <T> notFound() = ResponseEntity<T>(HttpStatus.NOT_FOUND)