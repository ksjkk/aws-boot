package com.example.bootaws.basic

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/basic")
class BasicController(
    private val basicService: BasicService
) {

    @PostMapping(value = ["/save"])
    fun save(@RequestBody item: Basic): Basic {
        println("save request param = $item")
        return basicService.save(item)
    }

    @GetMapping(value = ["/get/{id}"])
    fun getItem(@PathVariable id: Long): Basic? {
        println("get id = $id")
        return basicService.findById(id)
    }
}