package com.raassh.quiz2pbkk22.Quiz2PBKK22.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="review")
data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val rating: Int = 0,
    val review: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name="book_id")
    val book: Book? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name="user_id")
    val user: User? = null
)
