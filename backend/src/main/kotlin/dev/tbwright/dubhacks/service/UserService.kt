package dev.tbwright.dubhacks.service

import dev.tbwright.dubhacks.model.User
import dev.tbwright.dubhacks.repository.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    private val secretKey = "my-secret-key"  // Replace with your real secret key

    // Function to parse JWT and extract email
    fun extractEmailFromJwt(jwt: String): String {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(secretKey.toByteArray())  // Use the secret key to parse JWT
            .build()
            .parseClaimsJws(jwt)
            .body

        return claims.subject  // Typically, email is stored in the 'sub' field (subject)
    }

    // Check if the user exists by email, and create one if they don't exist
    fun getOrCreateUserFromJwt(jwt: String): User {
        val email = extractEmailFromJwt(jwt)

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
