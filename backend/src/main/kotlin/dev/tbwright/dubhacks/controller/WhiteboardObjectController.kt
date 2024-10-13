package dev.tbwright.dubhacks.controller

import dev.tbwright.dubhacks.dto.WhiteboardObjectDTO
import dev.tbwright.dubhacks.model.WhiteboardObject
import dev.tbwright.dubhacks.service.WhiteboardObjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/whiteboard-objects")
@CrossOrigin(origins = ["http://localhost:5173"])
class WhiteboardObjectController(private val whiteboardObjectService: WhiteboardObjectService) {

    @GetMapping("/{whiteboardId}")
    fun getAllWhiteboardObjects(@PathVariable whiteboardId: Long, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<List<WhiteboardObject>> {
        val whiteboardObjects = whiteboardObjectService.getWhiteboardObjects(whiteboardId, jwt.claims["email"] as String)
        return ResponseEntity.ok(whiteboardObjects)
    }

    @PostMapping
    fun createWhiteboardObject(@RequestBody whiteboardObject: WhiteboardObjectDTO, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<WhiteboardObject> {
        val newWhiteboardObject = whiteboardObjectService.createWhiteboardObject(whiteboardObject, jwt.claims["email"] as String)
        return ResponseEntity.status(HttpStatus.CREATED).body(newWhiteboardObject)
    }

    @PutMapping("/{id}")
    fun updateWhiteboardObject(@PathVariable id: Long, @RequestBody updatedWhiteboardObject: WhiteboardObjectDTO, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<WhiteboardObject> {
        val updated = whiteboardObjectService.updateWhiteboardObject(id, updatedWhiteboardObject, jwt.claims["email"] as String)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteWhiteboardObject(@PathVariable id: Long, @AuthenticationPrincipal jwt: Jwt): ResponseEntity<Void> {
        return if (whiteboardObjectService.deleteWhiteboardObject(id, jwt.claims["email"] as String)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
