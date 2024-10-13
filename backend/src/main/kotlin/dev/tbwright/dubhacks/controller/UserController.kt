package dev.tbwright.dubhacks.controller

import dev.tbwright.dubhacks.model.User
import dev.tbwright.dubhacks.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/me")
@CrossOrigin(origins = ["http://localhost:5173"])
class UserController(private val userService: UserService) {
    @GetMapping
    fun getUser(@AuthenticationPrincipal jwt: Jwt): ResponseEntity<User> {
        return ResponseEntity.ok(userService.getOrCreateUser(jwt.claims["email"] as String))
    }
}