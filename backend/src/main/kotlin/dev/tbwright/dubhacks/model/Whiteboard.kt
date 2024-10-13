package dev.tbwright.dubhacks.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "whiteboards")
data class Whiteboard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: User, // Foreign key relationship with User

    @NotBlank
    @Column(nullable = false)
    val name: String
)
