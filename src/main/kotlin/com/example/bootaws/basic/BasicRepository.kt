package com.example.bootaws.basic

import org.springframework.stereotype.Repository

@Repository
interface BasicRepository<T> {
    fun findById(id: Long): T
    fun findAll(): List<T>
    fun save(item: T): T
    fun deleteById(id: Long): T
}