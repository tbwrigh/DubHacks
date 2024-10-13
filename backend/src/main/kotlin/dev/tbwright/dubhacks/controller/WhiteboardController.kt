package dev.tbwright.dubhacks.controller

import dev.tbwright.dubhacks.model.Whiteboard
import dev.tbwright.dubhacks.service.WhiteboardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/whiteboards")
class WhiteboardController(private val whiteboardService: WhiteboardService) {

    @GetMapping
    fun getAllWhiteboards(): ResponseEntity<List<Whiteboard>> {
        val whiteboards = whiteboardService.getAllWhiteboards()
        return ResponseEntity.ok(whiteboards)
    }

    @GetMapping("/{id}")
    fun getWhiteboardById(@PathVariable id: Long): ResponseEntity<Whiteboard> {
        val whiteboard = whiteboardService.getWhiteboardById(id)
        return whiteboard.map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createWhiteboard(@RequestBody whiteboard: Whiteboard): ResponseEntity<Whiteboard> {
        val newWhiteboard = whiteboardService.createWhiteboard(whiteboard)
        return ResponseEntity.status(HttpStatus.CREATED).body(newWhiteboard)
    }

    @PutMapping("/{id}")
    fun updateWhiteboard(@PathVariable id: Long, @RequestBody updatedWhiteboard: Whiteboard): ResponseEntity<Whiteboard> {
        val updated = whiteboardService.updateWhiteboard(id, updatedWhiteboard)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteWhiteboard(@PathVariable id: Long): ResponseEntity<Void> {
        return if (whiteboardService.deleteWhiteboard(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
