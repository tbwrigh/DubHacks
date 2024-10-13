package dev.tbwright.dubhacks.controller

import dev.tbwright.dubhacks.annotation.Auth
import dev.tbwright.dubhacks.model.Whiteboard
import dev.tbwright.dubhacks.service.WhiteboardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/whiteboards")
class WhiteboardController(private val whiteboardService: WhiteboardService) {

    // Get all whiteboards owned by the logged-in user
    @Auth
    @GetMapping
    fun getAllWhiteboards(userEmail: String): ResponseEntity<List<Whiteboard>> {
        val whiteboards = whiteboardService.getAllWhiteboardsByUser(userEmail)
        return ResponseEntity.ok(whiteboards)
    }

    // Create a new whiteboard for the logged-in user
    @Auth
    @PostMapping
    fun createWhiteboard(@RequestBody whiteboard: Whiteboard, userEmail: String): ResponseEntity<Whiteboard> {
        val createdWhiteboard = whiteboardService.createWhiteboardForUser(whiteboard, userEmail)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWhiteboard)
    }

    // Update a whiteboard, ensure the user owns it
    @Auth
    @PutMapping("/{id}")
    fun updateWhiteboard(@PathVariable id: Long, @RequestBody updatedWhiteboard: Whiteboard, userEmail: String): ResponseEntity<Whiteboard> {
        // Ensure the whiteboard belongs to the logged-in user before updating
        val existingWhiteboard = whiteboardService.getWhiteboardByIdAndUserEmail(id, userEmail)
        return if (existingWhiteboard != null) {
            val updated = whiteboardService.updateWhiteboardForUser(id, updatedWhiteboard, userEmail)
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()  // Only the owner can update
        }
    }

    // Delete a whiteboard, ensure the user owns it
    @Auth
    @DeleteMapping("/{id}")
    fun deleteWhiteboard(@PathVariable id: Long, userEmail: String): ResponseEntity<Void> {
        // Ensure the whiteboard belongs to the logged-in user before deleting
        val success = whiteboardService.deleteWhiteboardForUser(id, userEmail)
        return if (success) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()  // Only the owner can delete
        }
    }
}
