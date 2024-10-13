package dev.tbwright.dubhacks.service

import dev.tbwright.dubhacks.dto.WhiteboardDTO
import dev.tbwright.dubhacks.model.Whiteboard
import dev.tbwright.dubhacks.repository.WhiteboardRepository
import org.springframework.stereotype.Service

@Service
class WhiteboardService(
    private val whiteboardRepository: WhiteboardRepository,
    private val userService: UserService
) {

    // Get all whiteboards for a specific user
    fun getAllWhiteboardsByUser(userEmail: String): List<Whiteboard> {
        return whiteboardRepository.findByOwner(userService.getOrCreateUser(userEmail))
    }

    // Get a specific whiteboard by ID and ensure it belongs to the user
    fun getWhiteboardByIdAndUserEmail(id: Long, userEmail: String): Whiteboard? {
        return whiteboardRepository
            .findByIdAndOwner(id, userService.getOrCreateUser(userEmail))
            .orElse(null)
    }

    // Create a new whiteboard for a specific user
    fun createWhiteboardForUser(whiteboard: WhiteboardDTO, userEmail: String): Whiteboard {
        val newWhiteboard = Whiteboard(name = whiteboard.name, owner = userService.getOrCreateUser(userEmail))
        return whiteboardRepository.save(newWhiteboard)
    }

    // Update a whiteboard, only if it belongs to the user
    fun updateWhiteboardForUser(id: Long, updatedWhiteboard: WhiteboardDTO, userEmail: String): Whiteboard? {
        val whiteboard = getWhiteboardByIdAndUserEmail(id, userEmail)
        return if (whiteboard != null) {
            val editedWhiteboard = whiteboard.copy(name = updatedWhiteboard.name)
            whiteboardRepository.save(editedWhiteboard)
        } else {
            null
        }
    }

    // Delete a whiteboard, only if it belongs to the user
    fun deleteWhiteboardForUser(id: Long, userEmail: String): Boolean {
        return if (getWhiteboardByIdAndUserEmail(id, userEmail) != null) {
            whiteboardRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
