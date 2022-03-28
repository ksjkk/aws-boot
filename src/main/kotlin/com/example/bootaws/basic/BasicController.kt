package com.example.bootaws.basic

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class BasicController(
    @Value("\${spring.profiles.active}")
    private val profile: String?,

    @Value("\${server.port}")
    private val port: String?
) {


    @GetMapping(value = ["/ping"])
    fun ping() = "pong".wrapOk()

    @GetMapping(value = ["/health"])
    @ResponseBody
    fun healthCheck() = "ok"

    @GetMapping(value = ["/profile"])
    fun profile() = "profile: $profile // port: $port".wrapOk()

    private fun <T> T?.wrapOk() = ResponseEntity.ok(this)
}