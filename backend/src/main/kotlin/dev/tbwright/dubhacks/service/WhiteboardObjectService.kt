package dev.tbwright.dubhacks.service

import dev.tbwright.dubhacks.dto.WhiteboardObjectDTO
import dev.tbwright.dubhacks.model.Whiteboard
import dev.tbwright.dubhacks.model.WhiteboardObject
import dev.tbwright.dubhacks.repository.WhiteboardObjectRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class WhiteboardObjectService(
    private val whiteboardObjectRepository: WhiteboardObjectRepository,
    private val whiteboardService: WhiteboardService
) {

    fun getWhiteboardObjects(whiteboardId: Long, userEmail: String): List<WhiteboardObject> {
        val whiteboard = whiteboardService.getWhiteboardByIdAndUserEmail(whiteboardId, userEmail)
            ?: return emptyList()
        return whiteboardObjectRepository.findByWhiteboard(whiteboard)
    }

    fun createWhiteboardObject(whiteboardObject: WhiteboardObjectDTO, userEmail: String): WhiteboardObject? {
        val whiteboard =
            whiteboardService.getWhiteboardByIdAndUserEmail(whiteboardObject.whiteboardId, userEmail)
                ?: return null

        val newWBObject = WhiteboardObject(
            whiteboard = whiteboard,
            posX = whiteboardObject.posX,
            posY = whiteboardObject.posY,
            data = whiteboardObject.data
        )
        return whiteboardObjectRepository.save(newWBObject)
    }

    fun updateWhiteboardObject(
        id: Long,
        updatedWhiteboardObject: WhiteboardObjectDTO,
        userEmail: String
    ): WhiteboardObject? {
        if (!objectOwnedByUser(id, userEmail)) {
            return null
        }

        return whiteboardObjectRepository.findById(id).map { existingWhiteboardObject ->
            val whiteboardObjectToUpdate = existingWhiteboardObject.copy(
                posX = updatedWhiteboardObject.posX,
                posY = updatedWhiteboardObject.posY,
                data = updatedWhiteboardObject.data,
            )
            whiteboardObjectRepository.save(whiteboardObjectToUpdate)
        }.orElse(null)
    }

    fun deleteWhiteboardObject(id: Long, userEmail: String): Boolean {
        return if (whiteboardObjectRepository.existsById(id) && objectOwnedByUser(id, userEmail)) {
            whiteboardObjectRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun objectOwnedByUser(id: Long, userEmail: String): Boolean {
        return whiteboardObjectRepository.findById(id).get().whiteboard.owner.email == userEmail
    }
}
