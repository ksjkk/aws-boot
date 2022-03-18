package com.example.bootaws.basic

import org.springframework.stereotype.Component

@Component
class BasicRepositoryLocalStorage(
    private val store: HashMap<Long, Basic> = HashMap()
): BasicRepository<Basic> {
    override fun findById(id: Long): Basic? {
        return store[id]
    }

    override fun findAll(): List<Basic> {
        return store.map { it.value }
    }

    override fun save(item: Basic): Basic {
        store[item.id] = item
        return item
    }

    override fun deleteById(id: Long): Boolean {
        return if (store[id] == null) {
            false
        } else {
            store.remove(id)
            true
        }
    }
}