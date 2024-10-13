package dev.tbwright.dubhacks.service

import dev.tbwright.dubhacks.model.Whiteboard
import dev.tbwright.dubhacks.repository.WhiteboardRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class WhiteboardService(private val whiteboardRepository: WhiteboardRepository) {

    fun getAllWhiteboards(): List<Whiteboard> = whiteboardRepository.findAll()

    fun getWhiteboardById(id: Long): Optional<Whiteboard> = whiteboardRepository.findById(id)

    fun createWhiteboard(whiteboard: Whiteboard): Whiteboard = whiteboardRepository.save(whiteboard)

    fun updateWhiteboard(id: Long, updatedWhiteboard: Whiteboard): Whiteboard? {
        return whiteboardRepository.findById(id).map { existingWhiteboard ->
                val whiteboardToUpdate = existingWhiteboard.copy(
                name = updatedWhiteboard.name,
                owner = updatedWhiteboard.owner
        )
            whiteboardRepository.save(whiteboardToUpdate)
        }.orElse(null)
    }

    fun deleteWhiteboard(id: Long): Boolean {
        return if (whiteboardRepository.existsById(id)) {
            whiteboardRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
