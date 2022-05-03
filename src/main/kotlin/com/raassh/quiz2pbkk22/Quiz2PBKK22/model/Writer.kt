package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "writer")

data class Writer (
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

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "book_writer",
        joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "writer_id", referencedColumnName = "id")])
    val books: List<Book>? = null
)