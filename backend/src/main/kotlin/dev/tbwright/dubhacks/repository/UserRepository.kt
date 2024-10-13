package dev.tbwright.dubhacks.repository

import dev.tbwright.dubhacks.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    // Define method to find user by email
    fun findByEmail(email: String): Optional<User>
}
