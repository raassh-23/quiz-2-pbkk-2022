package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "writers")
class Writer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String = "",
    var address: String = "",
    var email: String = "",
    var phone: String = "",
    var image_url: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    var updated_at: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(mappedBy = "writers")
    val books: MutableList<Book> = mutableListOf()
)