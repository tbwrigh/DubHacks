package dev.tbwright.dubhacks.controller

import dev.tbwright.dubhacks.annotation.Auth
import dev.tbwright.dubhacks.model.WhiteboardObject
import dev.tbwright.dubhacks.service.WhiteboardObjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/whiteboard-objects")
class WhiteboardObjectController(private val whiteboardObjectService: WhiteboardObjectService) {

    @Auth
    @GetMapping("/{whiteboardId}")
    fun getAllWhiteboardObjects(@PathVariable whiteboardId: Long, userEmail: String): ResponseEntity<List<WhiteboardObject>> {
        val whiteboardObjects = whiteboardObjectService.getWhiteboardObjects(whiteboardId, userEmail)
        return ResponseEntity.ok(whiteboardObjects)
    }

    @Auth
    @PostMapping
    fun createWhiteboardObject(@RequestBody whiteboardObject: WhiteboardObject, userEmail: String): ResponseEntity<WhiteboardObject> {
        val newWhiteboardObject = whiteboardObjectService.createWhiteboardObject(whiteboardObject, userEmail)
        return ResponseEntity.status(HttpStatus.CREATED).body(newWhiteboardObject)
    }

    @Auth
    @PutMapping("/{id}")
    fun updateWhiteboardObject(@PathVariable id: Long, @RequestBody updatedWhiteboardObject: WhiteboardObject, userEmail: String): ResponseEntity<WhiteboardObject> {
        val updated = whiteboardObjectService.updateWhiteboardObject(id, updatedWhiteboardObject, userEmail)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Auth
    @DeleteMapping("/{id}")
    fun deleteWhiteboardObject(@PathVariable id: Long, userEmail: String): ResponseEntity<Void> {
        return if (whiteboardObjectService.deleteWhiteboardObject(id, userEmail)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
