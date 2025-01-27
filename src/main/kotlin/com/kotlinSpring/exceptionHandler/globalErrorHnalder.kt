package com.kotlinSpring.exceptionHandler

import com.kotlinSpring.exception.InstructorNotValidException
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import java.net.http.HttpHeaders


@Component
@ControllerAdvice
class globalErrorHnalder : ResponseEntityExceptionHandler() {


    companion object: KLogging()
    fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request:WebRequest
    ): ResponseEntity<Any>{

       logger.error("MethodArgumentNotValidException observed: ${ex.message}", ex)
 val errors= ex.bindingResult.allErrors
     .map { error -> error .defaultMessage!! }
     .sorted()
       logger.info("errors : $errors")
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errors.joinToString(", "){it})
    }

    @ExceptionHandler(Exception::class)
    fun handleAllException(ex:Exception,request: WebRequest): ResponseEntity<Any>{
    logger.error("Exception observed : ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.message)

    }
    @ExceptionHandler(InstructorNotValidException::class)
    fun handleInstructorNotValidExceptions(ex:InstructorNotValidException,request: WebRequest): ResponseEntity<Any>{
        logger.error("Exception observed : ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ex.message)

    }
}