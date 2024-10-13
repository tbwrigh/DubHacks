package dev.tbwright.dubhacks.model

import jakarta.persistence.*

@Entity
@Table(name = "whiteboard_objects")
data class WhiteboardObject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whiteboard_id", nullable = false)
    val whiteboard: Whiteboard, // Foreign key relationship with Whiteboard

    @Column(nullable = false)
    val posX: Float, // Non-nullable float for position X

    @Column(nullable = false)
    val posY: Float, // Non-nullable float for position Y

    @Lob
    @Column(columnDefinition = "json")
    val data: String // Storing data as JSON string
)
