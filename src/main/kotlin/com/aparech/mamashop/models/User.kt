package com.aparech.mamashop.models

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue
        var id : Long?,

        @Column(nullable = false)
        var fullName : String,

        @Column(nullable = false)
        var contact : String,

        @Column(nullable = false)
        var address : String,

        @Column(nullable = false)
        var bday : Date,

        @OneToOne(cascade = [CascadeType.ALL], mappedBy = "user")
        val login : Login,

        @CreatedDate
        @Column(name = "created_at", updatable = false)
        var createdAt: LocalDateTime?,

        @LastModifiedDate
        @Column(name = "updated_at")
        var updatedAt: LocalDateTime?
)