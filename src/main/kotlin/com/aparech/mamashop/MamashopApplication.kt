package com.aparech.mamashop

import com.aparech.mamashop.models.User
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MamashopApplication

fun main(args: Array<String>) {
	runApplication<MamashopApplication>(*args){
		setBannerMode(Banner.Mode.OFF)
	}
}
