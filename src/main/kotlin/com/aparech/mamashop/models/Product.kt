package com.aparech.mamashop.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: Long?,

        @Column(nullable = false, length = 50)
        var name: String,

        @Column(nullable = false)
        var quantity: Int,

        @Column(nullable = false)
        var price: Float,

        @CreatedDate
        @Column(name = "created_at", updatable = false)
        var createdAt: LocalDateTime?,

        @LastModifiedDate
        @Column(name = "updated_at")
        var updatedAt: LocalDateTime?
) {

}