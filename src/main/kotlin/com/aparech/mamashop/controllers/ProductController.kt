package com.aparech.mamashop.controllers

import com.aparech.mamashop.models.Product
import com.aparech.mamashop.repositories.ProductRepository
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
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class ProductController(@Autowired
                        val productRepository : ProductRepository){

    @CrossOrigin
    @PostMapping
    fun createProduct(@Valid @RequestBody product:Product, uriComponentsBuilder: UriComponentsBuilder) : ResponseEntity<Product>{
        if(productRepository.findByName(product.name) != null){
            throw ResponseStatusException(HttpStatus.FOUND, "This product already exist")
        }

        productRepository.save(product)

        val headers = HttpHeaders()
        headers.location = uriComponentsBuilder.path("/api/products/{productName}").buildAndExpand(product.id).toUri()
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @CrossOrigin
    @PutMapping("/{id}")
    fun updateProduct(@Valid @RequestBody product:Product, @PathVariable("id") id:Long) : ResponseEntity<Product>{
        val foundProduct = productRepository.findById(id).get()
        foundProduct.name = product.name
        foundProduct.price = product.price
        foundProduct.quantity = product.quantity

        productRepository.save(foundProduct)

        return ResponseEntity(product, HttpStatus.OK)
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable("id") id:Long) : ResponseEntity<Product>{
        productRepository.deleteById(id)

        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/")
    fun findAll(@PageableDefault(size = 15)
                pageable: Pageable,
                @RequestParam(required = false, defaultValue = "id") sort : String,
                @RequestParam(required = false, defaultValue = "asc") order : String
    ) : ResponseEntity<List<Product>>{
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

        val productsPage = productRepository.findAll(pr)

        if(productsPage.content.isEmpty()){
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }else{
            val totalProducts = productsPage.totalElements
            val nbPageProducts = productsPage.numberOfElements

            val headers = HttpHeaders()
            headers.add("X-Total-Count", totalProducts.toString())

            if(nbPageProducts < totalProducts){
                headers.add("first", buildPageUri(PageRequest.of(0, productsPage.size)))
                headers.add("last", buildPageUri(PageRequest.of(productsPage.totalPages - 1, productsPage.size)))

                if(productsPage.hasNext()){
                    headers.add("next", buildPageUri(productsPage.nextPageable()))
                }

                if(productsPage.hasPrevious()){
                    headers.add("prev", buildPageUri(productsPage.previousPageable()))
                }

                return ResponseEntity(productsPage.content, headers, HttpStatus.PARTIAL_CONTENT)
            }else{
                return ResponseEntity(productsPage.content, headers, HttpStatus.OK)
            }
        }
    }

    private fun buildPageUri(page: Pageable): String? {
        return UriComponentsBuilder.fromUriString("/products")
                .query("page={page}&size={size}")
                .buildAndExpand(page.pageNumber, page.pageSize)
                .toUriString()
    }
}