package com.aparech.mamashop.repositories

import com.aparech.mamashop.models.Product
import com.aparech.mamashop.models.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.PagingAndSortingRepository

interface ProductRepository : PagingAndSortingRepository<Product, Long> {
    fun findByName(name: String): Product?
}