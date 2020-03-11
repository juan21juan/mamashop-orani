package com.aparech.mamashop.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue

@Entity
class User(
        @Id
        @GeneratedValue
        var id : Long ? = null,
        var fullName : String,
        val username : String,
        val password : String,
        val userType : String,
        val contact : String,
        val address : String
)