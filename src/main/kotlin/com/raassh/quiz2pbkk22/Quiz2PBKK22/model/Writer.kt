package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "writers")
class Writer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String = "",
    val address: String = "",
    val email: String = "",
    val phone: String = "",
    val image_url: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    val updated_at: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(mappedBy = "writers")
    val books: List<Book>? = null
)