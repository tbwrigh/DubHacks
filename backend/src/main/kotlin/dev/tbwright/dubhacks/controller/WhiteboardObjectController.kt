package dev.tbwright.dubhacks.controller

import dev.tbwright.dubhacks.model.WhiteboardObject
import dev.tbwright.dubhacks.service.WhiteboardObjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/whiteboard-objects")
class WhiteboardObjectController(private val whiteboardObjectService: WhiteboardObjectService) {

    @GetMapping
    fun getAllWhiteboardObjects(): ResponseEntity<List<WhiteboardObject>> {
        val whiteboardObjects = whiteboardObjectService.getAllWhiteboardObjects()
        return ResponseEntity.ok(whiteboardObjects)
    }

    @GetMapping("/{id}")
    fun getWhiteboardObjectById(@PathVariable id: Long): ResponseEntity<WhiteboardObject> {
        val whiteboardObject = whiteboardObjectService.getWhiteboardObjectById(id)
        return whiteboardObject.map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createWhiteboardObject(@RequestBody whiteboardObject: WhiteboardObject): ResponseEntity<WhiteboardObject> {
        val newWhiteboardObject = whiteboardObjectService.createWhiteboardObject(whiteboardObject)
        return ResponseEntity.status(HttpStatus.CREATED).body(newWhiteboardObject)
    }

    @PutMapping("/{id}")
    fun updateWhiteboardObject(@PathVariable id: Long, @RequestBody updatedWhiteboardObject: WhiteboardObject): ResponseEntity<WhiteboardObject> {
        val updated = whiteboardObjectService.updateWhiteboardObject(id, updatedWhiteboardObject)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteWhiteboardObject(@PathVariable id: Long): ResponseEntity<Void> {
        return if (whiteboardObjectService.deleteWhiteboardObject(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
