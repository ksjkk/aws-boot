package com.example.bootaws.basic

import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class BasicService(
    private val basicRepository: BasicRepositoryLocalStorage
) {
    private val log = KotlinLogging.logger {}

    private fun logAllItem(){
        val list = basicRepository.findAll()
        log.info("list size : ${list.size}")
        list.forEach { log.info(it.toString()) }
    }

    fun save(item: Basic) = basicRepository.save(item).wrapItem()

    fun findById(id: Long) = basicRepository.findById(id).wrapItem()

    fun deleteById(id: Long) = basicRepository.deleteById(id).wrapItem()

    private fun<T> T.wrapItem(): T {
        logAllItem()
        return this
    }
}