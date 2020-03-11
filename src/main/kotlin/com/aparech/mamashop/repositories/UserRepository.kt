package com.aparech.mamashop.repositories

import com.aparech.mamashop.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
}