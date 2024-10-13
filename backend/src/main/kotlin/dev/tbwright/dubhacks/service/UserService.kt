package dev.tbwright.dubhacks.service

import dev.tbwright.dubhacks.model.User
import dev.tbwright.dubhacks.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    // Check if the user exists by email, and create one if they don't exist
    fun getOrCreateUser(email: String): User {

        // Check if user already exists by email
        val userOptional = userRepository.findByEmail(email)

        return userOptional.orElseGet {
            // If user doesn't exist, create and save a new user
            val newUser = User(
                name = email,  // Defaulting name to email, can be changed later
                email = email
            )
            userRepository.save(newUser)
        }
    }
}
