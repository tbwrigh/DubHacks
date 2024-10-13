package dev.tbwright.dubhacks.repository

import dev.tbwright.dubhacks.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
