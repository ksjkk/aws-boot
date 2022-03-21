package com.example.bootaws.basic

import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class BasicRepositoryLocalStorage(
    private var store: MutableList<Basic> = mutableListOf()
): BasicRepository<Basic> {
    companion object {
        var id: Long = 0
        fun plusId(): Long {
            return id++
        }
    }

    override fun findById(id: Long): Basic {
        return store.find { it.id == id } ?: throw RuntimeException("Entity Not Found")
    }

    override fun findAll(): List<Basic> {
        return store
    }

    override fun save(item: Basic): Basic {
        item.id = plusId()
        store.add(item)
        return item
    }

    override fun deleteById(id: Long): Basic {
        val basic = findById(id)
        store.remove(basic)
        return basic
    }
}