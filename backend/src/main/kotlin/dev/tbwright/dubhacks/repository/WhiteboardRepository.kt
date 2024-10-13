package dev.tbwright.dubhacks.repository

import dev.tbwright.dubhacks.model.Whiteboard
import org.springframework.data.jpa.repository.JpaRepository

interface WhiteboardRepository : JpaRepository<Whiteboard, Long>
