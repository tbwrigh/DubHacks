package dev.tbwright.dubhacks.service

import dev.tbwright.dubhacks.model.WhiteboardObject
import dev.tbwright.dubhacks.repository.WhiteboardObjectRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class WhiteboardObjectService(private val whiteboardObjectRepository: WhiteboardObjectRepository) {

    fun getAllWhiteboardObjects(): List<WhiteboardObject> = whiteboardObjectRepository.findAll()

    fun getWhiteboardObjectById(id: Long): Optional<WhiteboardObject> = whiteboardObjectRepository.findById(id)

    fun createWhiteboardObject(whiteboardObject: WhiteboardObject): WhiteboardObject = whiteboardObjectRepository.save(whiteboardObject)

    fun updateWhiteboardObject(id: Long, updatedWhiteboardObject: WhiteboardObject): WhiteboardObject? {
        return whiteboardObjectRepository.findById(id).map { existingWhiteboardObject ->
            val whiteboardObjectToUpdate = existingWhiteboardObject.copy(
                posX = updatedWhiteboardObject.posX,
                posY = updatedWhiteboardObject.posY,
                data = updatedWhiteboardObject.data,
                whiteboard = updatedWhiteboardObject.whiteboard
            )
            whiteboardObjectRepository.save(whiteboardObjectToUpdate)
        }.orElse(null)
    }

    fun deleteWhiteboardObject(id: Long): Boolean {
        return if (whiteboardObjectRepository.existsById(id)) {
            whiteboardObjectRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
