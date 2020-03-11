package com.aparech.mamashop.controllers

import com.aparech.mamashop.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController (private val userRepository: UserRepository){
    @GetMapping("/")
    fun findAll() = userRepository.findAll()

    @GetMapping("/{username}")
    fun findUser(@PathVariable username:String)
    = userRepository.findByUsername(username)
            ?: ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}