package com.example.bootaws.basic

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class BasicController {

    @GetMapping(value = ["/ping"])
    fun ping() = "pong".wrapOk()

    @GetMapping(value = ["/health"])
    @ResponseBody
    fun healthCheck() = "ok"

    private fun <T> T?.wrapOk() = ResponseEntity.ok(this)
}