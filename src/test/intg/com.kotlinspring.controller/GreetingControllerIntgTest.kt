package com.kotlinspring.controller

import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
class GreetingControllerIntgTest {

    @Autowired
    lateinit var  webTestClient: WebTestClient

    @Test
    fun retrievGreeting(){
        val name= "Dilip"
       val result=  webTestClient.get()
            .uri("/v1/greetings/{name}",name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()
        Assertions.assertEquals("$name, Hello from default profile ", result.responseBody)
    }


}