package dev.tbwright.dubhacks.controller

import dev.tbwright.dubhacks.dto.WhiteboardDTO
import dev.tbwright.dubhacks.model.Whiteboard
import dev.tbwright.dubhacks.service.WhiteboardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/whiteboards")
@CrossOrigin(origins = ["http://localhost:5173"])
class WhiteboardController(private val whiteboardService: WhiteboardService) {

    // Get all whiteboards owned by the logged-in user
    @GetMapping
    fun getAllWhiteboards(@AuthenticationPrincipal jwt: Jwt): ResponseEntity<List<Whiteboard>> {
        val whiteboards = whiteboardService.getAllWhiteboardsByUser(jwt.claims["email"] as String)
        return ResponseEntity.ok(whiteboards)
    }

    // Create a new whiteboard for the logged-in user
    @PostMapping
    fun createWhiteboard(@RequestBody whiteboard: WhiteboardDTO, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<Whiteboard> {
        val createdWhiteboard = whiteboardService.createWhiteboardForUser(whiteboard, jwt.claims["email"] as String)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWhiteboard)
    }

    // Update a whiteboard, ensure the user owns it
    @PutMapping("/{id}")
    fun updateWhiteboard(@PathVariable id: Long, @RequestBody updatedWhiteboard: WhiteboardDTO, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<Whiteboard> {
        // Ensure the whiteboard belongs to the logged-in user before updating
        val existingWhiteboard = whiteboardService.getWhiteboardByIdAndUserEmail(id, jwt.claims["email"] as String)
        return if (existingWhiteboard != null) {
            val updated = whiteboardService.updateWhiteboardForUser(id, updatedWhiteboard, jwt.claims["email"] as String)
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()  // Only the owner can update
        }
    }

    // Delete a whiteboard, ensure the user owns it
    @DeleteMapping("/{id}")
    fun deleteWhiteboard(@PathVariable id: Long, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<Void> {
        // Ensure the whiteboard belongs to the logged-in user before deleting
        val success = whiteboardService.deleteWhiteboardForUser(id, jwt.claims["email"] as String)
        return if (success) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()  // Only the owner can delete
        }
    }
}
