package dev.tbwright.dubhacks.repository

import dev.tbwright.dubhacks.model.Whiteboard
import dev.tbwright.dubhacks.model.WhiteboardObject
import org.springframework.data.jpa.repository.JpaRepository

interface WhiteboardObjectRepository : JpaRepository<WhiteboardObject, Long> {
    fun findByWhiteboard(whiteboard: Whiteboard): List<WhiteboardObject>
}
