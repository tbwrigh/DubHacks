package dev.tbwright.dubhacks.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String? = null, // Nullable name field

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    val email: String
)
