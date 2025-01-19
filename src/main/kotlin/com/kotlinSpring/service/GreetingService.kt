package com.kotlinSpring.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service

class GreetingService {
    @Value("\${message}")
    lateinit var message:String
    fun retrieveGreeting(name:String)="$name, $message"
}