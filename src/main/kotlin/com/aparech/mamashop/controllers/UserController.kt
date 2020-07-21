package com.aparech.mamashop.controllers

import com.aparech.mamashop.models.User
import com.aparech.mamashop.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder.fromUriString
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/users")
class UserController(@Autowired val userRepository: UserRepository) {

    @GetMapping("/")
    fun findAll(@PageableDefault(size = 15)
                pageable: Pageable,
                @RequestParam(required = false, defaultValue = "id") sort : String,
                @RequestParam(required = false, defaultValue = "asc") order : String
                ) : ResponseEntity<List<User>>{
        val pr = PageRequest.of(
                 pageable.pageNumber,
                 pageable.pageSize,
                 Sort.by(
                         if("asc".equals(order)){
                             Sort.Direction.ASC
                         }else{
                             Sort.Direction.DESC
                         },
                 sort))

        val usersPage = userRepository.findAll(pr)

        if(usersPage.content.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }else{
            val totalUsers = usersPage.totalElements
            val nbPageUsers = usersPage.numberOfElements

            val headers = HttpHeaders()
            headers.add("X-Total-Count", totalUsers.toString())

            if(nbPageUsers < totalUsers){
                headers.add("first", buildPageUri(PageRequest.of(0, usersPage.size)))
                headers.add("last", buildPageUri(PageRequest.of(usersPage.totalPages - 1, usersPage.size)))

                if(usersPage.hasNext()){
                    headers.add("next", buildPageUri(usersPage.nextPageable()))
                }

                if(usersPage.hasPrevious()){
                    headers.add("prev", buildPageUri(usersPage.previousPageable()))
                }

                return ResponseEntity(usersPage.content, headers, HttpStatus.PARTIAL_CONTENT)
            }else{
                return ResponseEntity(usersPage.content, headers, HttpStatus.OK)
            }
        }
    }

    @PostMapping
    fun createUser(@Valid @RequestBody user:User, uriComponentsBuilder: UriComponentsBuilder) : ResponseEntity<User>{
        if(userRepository.findByFullName(user.fullName) != null){
            throw ResponseStatusException(HttpStatus.FOUND, "This user already exist")
        }

        userRepository.save(user)

        val headers = HttpHeaders()
        headers.location = uriComponentsBuilder.path("/api/user/{username}").buildAndExpand(user.id).toUri()
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    private fun buildPageUri(page: Pageable): String? {
        return fromUriString("/users")
                .query("page={page}&size={size}")
                .buildAndExpand(page.pageNumber, page.pageSize)
                .toUriString()
    }
}