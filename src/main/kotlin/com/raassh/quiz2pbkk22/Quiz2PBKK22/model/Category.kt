package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    var updated_at: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "category")
    val books: List<Book>? = null
)