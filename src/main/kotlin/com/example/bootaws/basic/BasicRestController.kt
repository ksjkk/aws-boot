package com.example.bootaws.basic

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/basic")
class BasicRestController(
    private val basicService: BasicService
) {
    private val log = KotlinLogging.logger {}

    @PostMapping(value = ["/save"])
    fun save(@RequestBody item: Basic): Basic {
        log.debug("save request param = $item")
        return basicService.save(item)
    }

    @GetMapping(value = ["/get/{id}"])
    fun getItem(@PathVariable id: Long): Basic {
        log.debug("get id = $id")
        return basicService.findById(id)
    }

    @DeleteMapping(value = ["/delete/{id}"])
    fun deleteItem(@PathVariable id: Long): Basic {
        log.debug("delete id = $id")
        return basicService.deleteById(id)
    }
}