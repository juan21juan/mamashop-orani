package com.aparech.mamashop.repositories

import com.aparech.mamashop.models.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@AutoConfigureTestDatabase(replace= Replace.NONE)
@DataJpaTest
class RepositoriesTests @Autowired
        constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository){

    @Test
    fun `When findByUsername then return User`() {
        val john = User(null,"John Aparejado", "janjan", "123aaa", "ADMIN", "09091231231", "Orani, Bataan")
        entityManager.persist(john)
        entityManager.flush()

        val user = userRepository.findByUsername(john.username)
        assertThat(user).isEqualTo(john)
    }
}