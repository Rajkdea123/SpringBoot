package com.kotlinspring.controller

import com.kotlinSpring.controller.GreetingController
import com.kotlinSpring.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test


@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitText {

    @Autowired
    lateinit var  webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingServiceMock: GreetingService
    @Test
    fun retrievGreeting(){
        val name= "Dilip"
        every { greetingServiceMock.retrieveGreeting(any()) } returns "$name, Hello from default profile"
        val result=  webTestClient.get()
            .uri("/v1/greetings/{name}",name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()
        Assertions.assertEquals("$name, Hello from default profile ", result.responseBody)
    }

}