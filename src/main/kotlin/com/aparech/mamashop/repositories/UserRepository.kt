package com.aparech.mamashop.repositories

import com.aparech.mamashop.models.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : PagingAndSortingRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByFullName(fullName: String): User?
}