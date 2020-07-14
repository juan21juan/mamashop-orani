package com.aparech.mamashop.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue

@Entity
class User(
        @Id
        @GeneratedValue
        var id : Long,
        @Column(nullable = false)
        val username : String,
        @Column(nullable = false)
        var fullName : String,
        @Column(nullable = false)
        var password : String,
        @Column(nullable = false)
        var userType : String,
        @Column(nullable = false)
        var contact : String,
        @Column(nullable = false)
        var address : String
)