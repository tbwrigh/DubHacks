package dev.tbwright.dubhacks.repository

import dev.tbwright.dubhacks.model.User
import dev.tbwright.dubhacks.model.Whiteboard
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WhiteboardRepository : JpaRepository<Whiteboard, Long> {
    fun findByIdAndOwner(id: Long, owner: User): Optional<Whiteboard>

    fun findByOwner(owner: User): List<Whiteboard>
}
