package com.aparech.mamashop.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Login(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: Long?,

        @Column(nullable = false)
        val username: String,

        @Column(nullable = false)
        var password: String,

        @Column(nullable = false)
        var userType: String,

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "userId")
        @JsonIgnore
        var user: User,

        @CreatedDate
        @Column(name = "created_at", updatable = false)
        var createdAt: LocalDateTime?,

        @LastModifiedDate
        @Column(name = "updated_at")
        var updatedAt: LocalDateTime?
) {

}