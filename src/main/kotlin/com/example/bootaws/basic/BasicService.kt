package com.example.bootaws.basic

import org.springframework.stereotype.Service

@Service
class BasicService(
    private val basicRepository: BasicRepositoryLocalStorage
) {
    private fun allItem(){
        println(basicRepository.findAll())
    }

    fun save(item: Basic): Basic {
        val basic = basicRepository.save(item)
        allItem()
        return basic
    }

    fun findById(id: Long) : Basic? {
        val basic = basicRepository.findById(id)
        allItem()
        return basic
    }
}